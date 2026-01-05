package com.incloud.hcp.domain.balanza.convertir;

/**
 * 
 * @author Cristian M. Vargas Navarrete
 *
 */
public class BooleanConstants {
	
	public enum BooleanInteger {
        TRUE(1),
        FALSE(0);
    	
    	private final Integer value;

        private BooleanInteger( Integer value ) {
            this.value = value;
        }
        
        public Integer getValue() {
            return value;
        }
    }
    
    public enum BooleanCharacter {
    	TRUE('1'),
        FALSE('0');
    	
    	private final Character value;

    	private BooleanCharacter( Character value ) {
            this.value = value;
        }
        
        public Character getValue() {
            return value;
        }
    }
    
    public enum BooleanString {
    	TRUE("SI"),
        FALSE("N0");
    	
    	private final String value;

        private BooleanString(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }

    }
}
