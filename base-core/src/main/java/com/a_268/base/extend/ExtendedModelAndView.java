package com.a_268.base.extend;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import java.util.Map;


public class ExtendedModelAndView extends ModelAndView {
	
	public ExtendedModelAndView(Map<String, ?> model) {
		super();
		super.addAllObjects(model);	
	}

	public ExtendedModelAndView(String viewName, Map<String, ?> model) {
		super(viewName, model);
	}

	public ExtendedModelAndView(String viewName, String modelName,
			Object modelObject) {
		super(viewName, modelName, modelObject);
	}

	public ExtendedModelAndView(String viewName) {
		super(viewName);
	}


	public ExtendedModelAndView(View view, Map<String, ?> model) {
		super(view, model);
	}

	public ExtendedModelAndView(View view, String modelName, Object modelObject) {
		super(view, modelName, modelObject);
	}

	public ExtendedModelAndView(View view) {
		super(view);
		
	}

}
