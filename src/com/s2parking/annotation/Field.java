package com.s2parking.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Field {

	// Id do xml do campo
	int id();
	
	// Se o campo é ou não requerido
	boolean required() default false;
	
	// Se o campo deve ter um tamanho mínimo de caracteres (zero significa que não possui tamanho mínimo)
//	int minLength() default 0;

	//Id do recurso da mensagem
	int message() default 0;
}
