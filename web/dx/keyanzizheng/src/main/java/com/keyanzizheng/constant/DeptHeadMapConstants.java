package com.keyanzizheng.constant;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * 部门领导的ID
 *
 * @author YaoZhen
 * @date 05-29, 18:47, 2018.
 */
public class DeptHeadMapConstants {
    /**
     * 键是sysUserId
     * 值是页面上 处室下拉选择框的value
     * @see com.keyanzizheng.entity.subsection.SubSection
     */
    public static Map<Long, Integer> deptMap = new ImmutableMap.Builder<Long, Integer>()

            .put(2415L, 9)  // 赵  珊    办公室(领导)
            .put(2442L, 12) // 罗金耀    财务处 (领导)
            .put(2450L, 1)  // 朱新现    党史党建教研部(领导)
            .put(2451L, 1)  // 连晓畅    党史党建教研部(领导)
            .put(2412L, 99) // 高  勇	调研员(领导)
            .put(2454L, 4)  // 王天云	法学教研部(领导)
            .put(2460L, 2)  // 陈  亮	公共管理教研部(领导)
            .put(2467L, 13) // 晏家龙	后勤管理处(领导)
            .put(2433L, 10) // 张  颖	机关党委(领导)
            .put(2489L, 14) // 陈  军	纪检监察室 (领导)
            .put(2490L, 15) // 邱凤林	教务处（社会主义学院工作处）(领导)
            .put(2502L, 3)  // 吴桂华	经济学教研部(领导)
            .put(2507L, 17) // 余  红	科研管理处(领导)
            .put(2508L, 17) // 宁  宁	科研管理处(领导)
            .put(2512L, 6)  // 邓跃星	马列主义理论教研部(领导)
            .put(2513L, 6)  // 陈月平	马列主义理论教研部(领导)
            .put(2517L, 18) // 吴道军	生态文明研究所(领导)
            .put(2523L, 7)  // 贺祝群	统一战线理论教研部(领导)
            .put(2527L, 19) // 朱  莉	图书馆（《学报》编辑部、校史办）(领导)
            .put(2539L, 5)  // 庄品珽	文化与社会发展教研部(领导)
            .put(2406L, 8)  // 蔡定荣	校委委员(领导)
            .put(2545L, 16) // 苟宗杰	信息资源管理处(领导)
            .put(2553L, 20) // 程崇玉	学员管理处(领导)
            .put(2411L, 11) // 周晓军	组织人事处(领导)
            .put(2872L, 21) // 观山湖区党校
            .put(2873L, 22) // 云岩区党校
            .put(2874L, 23) // 南明区党校
            .put(2875L, 24) // 花溪区党校
            .put(2876L, 25) // 乌当区党校
            .put(2877L, 26) // 白云区党校
            .put(2878L, 27) // 修文县党校
            .put(2879L, 28) // 息烽县党校
            .put(2880L, 29) // 开阳县党校
            .put(2881L, 30) // 清镇市党校
            .build();



    /**
     * 科研处领导 宁宁的sysUserId
     */
    public static final Long REAL_LEADER = 2508L;
}
