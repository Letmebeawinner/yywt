package com.a_268.base.mybatis.codeBuilder;

import com.a_268.base.core.BaseEntity;
import com.a_268.base.core.IEntity;
import org.apache.ibatis.annotations.CacheNamespaceRef;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 继承自SqlSessionFactoryBean类，实现Mapper基本操作模块化功能
 * <p>
 * 	在一般系统中，每个Model都会有部分基本操作，这些操作模式基本相同，所以在我们系统内部将其抽象出来，通过动态的生成Mapper的形式注入到Mybatis中。<br/>
 * 	对于生成的mapper文件，在{@link #setMapperLocations(Resource[])}方法中加入到Factory map locations中，让Ibatis自行处理。
 * </p>
 * @see SqlSessionFactoryBean
 */
public class DynamicMapperSqlSessionFactoryBean extends SqlSessionFactoryBean {

    private static final Logger LOGGER = Logger.getLogger(DynamicMapperSqlSessionFactoryBean.class);
    /**
     * 再此方法中加入了动态mapper 文件插入
     *
     * @param mapperLocations mapper 资源文件列表
     */
    @Override
    public void setMapperLocations(Resource[] mapperLocations) {
        //生成mapper资源文件对象
        List<MapperSplitter> resources = productModelMapperResources();
        //按照是否有cacheNameSpaceRef进行排序，没有的排在前面！！Mybatis的bug：如果引用了别的缓存命名空间，而被引用的命名空间在引用者后面被解析的话，缓存设置无效。
        Collections.sort(resources, new Comparator<MapperSplitter>() {
            public int compare(MapperSplitter o1, MapperSplitter o2) {
                if(o1.isCacheNameSpaceRef() && o2.isCacheNameSpaceRef()) return 0;
                if(!o1.isCacheNameSpaceRef() && !o2.isCacheNameSpaceRef()) return 0;
                if(o1.isCacheNameSpaceRef() && !o2.isCacheNameSpaceRef()) return 1;
                return -1;
            }
        });

        //将原始source和生成的source整合
        try {
            super.setMapperLocations(addResourcesByArray(mapperLocations,resources));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private  String readResourceAsString(Resource resource){
        StringBuffer buffer = new StringBuffer();
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()),1024);

            while(true){
                String line = reader.readLine();
                if (line != null){
                    buffer.append(line);
                    buffer.append("\r\n");
                }else break;
            }
            reader.close();

            return buffer.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将自定义的mapper文件加到动态生成的mapper文件中。返回合并后的resource集合。
     * @param sources 资源文件列表
     * @param dynamicResources 动态生成的资源文件
     * @return 添加后的资源
     * @throws Exception
     */
    private Resource[] addResourcesByArray(Resource[] sources,List<MapperSplitter> dynamicResources) throws Exception{
        List<Resource> mergedResources = new ArrayList<Resource>();
        Map<String,MapperSplitter> mapperMap = new HashMap<String, MapperSplitter>();

        //把自定义的mapper文件按namespace进行拆分。目前namespace相同的mapper文件只能自定义到一个文件中去。
        for(Resource resource : sources){
            String mapperText = readResourceAsString(resource);
            MapperSplitter splitter = new MapperSplitter(mapperText,resource);
            mapperMap.put(splitter.getNamespace(), splitter);
        }
        //将生成的动态mapper文件和自定义的mapper文件进行合并。
        for(MapperSplitter dynamicMapper : dynamicResources){
            MapperSplitter mapper = mapperMap.get(dynamicMapper.getNamespace());
            if( mapper!=null ){
                String finalMapper = dynamicMapper.getMapperText().replace(dynamicMapper.getSqlNodes(), dynamicMapper.getSqlNodes() + mapper.getSqlNodes());
                mapperMap.remove(mapper.getNamespace());
                if(LOGGER.isDebugEnabled()){
                    //LOGGER.debug(finalMapper);
                }
                mergedResources.add( new InputStreamResource(new ByteArrayInputStream(finalMapper.getBytes("UTF-8")),mapper.getNamespace()));
            }else{ //如果同名的nampespace下没有自定义的mapper文件，则加入动态生成的mapper文件
                mergedResources.add( new InputStreamResource(new ByteArrayInputStream(dynamicMapper.getMapperText() .getBytes("UTF-8")),dynamicMapper.getNamespace()));
            }
        }
        //加入所有用户自定义的和动态生成合并后剩下的用户自定义的mapper文件。
        for(MapperSplitter splitter : mapperMap.values()){
            mergedResources.add(splitter.getResource());
        }

        return mergedResources.toArray(new Resource[]{});


    }

    /**
     * 根据当前entity类，自动生成mapper对应的基本文件
     */
    private List<MapperSplitter> productModelMapperResources(){

        List<Class<?>> list = getAllBaseClass();
        LOGGER.debug("classes size is " + list.size());
        //根据class生成相应的Mapper文件
        List<MapperSplitter> mappers = new ArrayList<MapperSplitter>();
        //开始生成
        for(int i = 0;i < list.size();i ++){
            String crudMapper = new MybatisCRUDBuilder().buildByClass(list.get(i));
            MapperSplitter splitter = new MapperSplitter(crudMapper);
            splitter.setCacheNameSpaceRef(list.get(i).isAnnotationPresent(CacheNamespaceRef.class));
            mappers.add(splitter);
        }
        return mappers;
    }

    /**
     * 获取JVM中所有Model类列表
     * @return Model类列表
     */
    private List<Class<?>> getAllBaseClass(){
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(Thread.currentThread().getContextClassLoader());
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
        List<Class<?>> classList = new ArrayList<Class<?>>();
        try {
            Resource[] rs = resolver.getResources("classpath*:com/**/entity/**/*.class");
            for(Resource resource : rs){
                String className = metadataReaderFactory.getMetadataReader(resource).getClassMetadata().getClassName();
                Class<?> clazz;
                try {
                    clazz = Class.forName(className);
                    //判断当前class是否是BaseEntity的子类，且不是BaseEntity
                    if(IEntity.class.isAssignableFrom(clazz) && clazz != IEntity.class && clazz != BaseEntity.class){
                        classList.add(clazz);
                    }
                } catch (ClassNotFoundException e) {
                    LOGGER.error("实体" + className +"应该有一个无参的构造函数...");
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classList;
    }

    /**
     * 把映射文件按正则表达式进行解析，为动态生成的和自己配的映射文件整合成一个文件做准备
     */
    private static class MapperSplitter {
        private static Pattern pattern = Pattern.compile("<mapper *namespace *= *\"(.+?)\" *>([\\S\\s]*)</mapper>");
        /**
         * 映射文件对应的文本内容
         */
        private String mapperText;
        /**
         * 映射文件的命名空间
         */
        private String namespace;
        /**
         * 映射文件中的 crud 对应的文本
         */
        private String sqlNodes;

        private boolean cacheNameSpaceRef = false;

        /**
         * 映射文件对应的resource
         */
        private Resource resource;

        MapperSplitter(String mapperText,Resource resource){
            this(mapperText);
            this.resource = resource;
        }
        MapperSplitter(String mapperText){
            this.mapperText = mapperText;

            Matcher matcher = pattern.matcher(mapperText);
            if(matcher.find()){
                this.namespace = matcher.group(1);
                this.sqlNodes = matcher.group(2);
            }else{
                throw new RuntimeException();
            }
        }

        public boolean isCacheNameSpaceRef() {
            return cacheNameSpaceRef;
        }
        public void setCacheNameSpaceRef(boolean cacheNameSpaceRef) {
            this.cacheNameSpaceRef = cacheNameSpaceRef;
        }

        public Resource getResource(){
            return this.resource;
        }

        public String getNamespace() {
            return namespace;
        }

        public String getSqlNodes() {
            return sqlNodes;
        }

        public String getMapperText() {
            return mapperText;
        }
    }

}
