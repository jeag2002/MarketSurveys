package es.caravelloserver.utils;

import java.util.UUID;

public class StringUtils {
	
	/**
	 * Get Unique Identifier
	 * @return
	 */
	
	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	
}
