package com.codepath.wangela.apps.rocklizardspock.models;

import com.facebook.model.OpenGraphAction;
import com.facebook.model.OpenGraphObject;

public class OpenGraphUtils {

	public interface RLSAction extends OpenGraphAction {
	       public static String TYPE = "rock-lizard-spock:crush";
	       public static String PATH = "me/" + TYPE;
	        public static final String PREVIEW_PROPERTY_NAME = "gesture";
	        
	        String getOWeapon();
	        void setOWeapon(String oWeapon);
	        
	        String getType();
	        void setType(String type);
	}
	
	public interface RLSWeapon extends OpenGraphObject {
		public static String TYPE = "objects/rock-lizard-spock:lizard";
		public static final String PREVIEW_PROPERTY_NAME = "weapon";
		public static String TITLE = "lizard";
		
		String getType();
		void setType(String type);
		
		String getTitle();
		void setTitle(String title);
	}
	
}
