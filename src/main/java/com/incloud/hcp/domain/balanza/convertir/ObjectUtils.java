package com.incloud.hcp.domain.balanza.convertir;

import java.util.List;

/**
 * 
 * @author Jenny C. Ancajima Valencia
 *
 */
public class ObjectUtils {

	public static boolean isNull(Object value) {
	    return value == null ? Boolean.TRUE : Boolean.FALSE;
	}
	
	public static boolean isEmpty(Object value) {
		if (value instanceof String) {
			return (value == null || ((String) value).isEmpty()) ? Boolean.TRUE : Boolean.FALSE;
		} else if (value instanceof Number) {
			return (value == null || ((Number) value).intValue() <= 0) ? Boolean.TRUE : Boolean.FALSE;
		} else if (value instanceof List) {
			return (value == null || ((List) value).isEmpty()) ? Boolean.TRUE : Boolean.FALSE;
		}else {
			return value == null ? Boolean.TRUE : Boolean.FALSE;
		}
	}
}
