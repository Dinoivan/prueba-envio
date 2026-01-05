package com.incloud.hcp.domain.balanza.convertir;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * 
 * @author Cristian M. Vargas Navarrete
 *
 */
public class BooleanConverters {

	@Converter()
	public static class CharacterConverter implements AttributeConverter<Boolean, Character> {

		@Override
		public Character convertToDatabaseColumn( Boolean attribute ) {
			
			if ( null != attribute && attribute ) {
				return BooleanConstants.BooleanCharacter.TRUE.getValue();
			} else {
				return BooleanConstants.BooleanCharacter.FALSE.getValue();
			}
		}

		@Override
		public Boolean convertToEntityAttribute( Character dbData ) {
			
			if ( dbData ==  BooleanConstants.BooleanCharacter.TRUE.getValue() ) {
				return true;
			}else {
				return false;
			}		
		}
	}

	@Converter()
	public static class IntegerConverter implements AttributeConverter<Boolean, Integer> {

		@Override
		public Integer convertToDatabaseColumn( Boolean attribute ) {
			
			if ( null == attribute || attribute ) {
				return BooleanConstants.BooleanInteger.TRUE.getValue();
			} else {
				return BooleanConstants.BooleanInteger.FALSE.getValue();
			}
		}

		@Override
		public Boolean convertToEntityAttribute( Integer dbData ) {
			
			if ( dbData ==  BooleanConstants.BooleanInteger.TRUE.getValue() ) {
				return true;
			}else {
				return false;
			}		
		}
	}
	
	@Converter()
	public static class StringConverter implements AttributeConverter<Boolean, String> {

		@Override
		public String convertToDatabaseColumn( Boolean attribute ) {
			
			if ( null == attribute || attribute ) {
				return BooleanConstants.BooleanString.TRUE.getValue();
			} else {
				return BooleanConstants.BooleanString.FALSE.getValue();
			}
		}

		@Override
		public Boolean convertToEntityAttribute( String dbData ) {
			
			if ( dbData ==  BooleanConstants.BooleanString.TRUE.getValue() ) {
				return true;
			}else {
				return false;
			}		
		}
	}
}
