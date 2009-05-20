package org.samples;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.jgentleframework.configure.annotation.BeanServices;
import org.jgentleframework.context.JGentle;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.core.handling.DefinitionManager;
import org.jgentleframework.core.reflection.metadata.Definition;

/**
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Nov 4, 2007
 */
public class DefinitionTest {
	public static void main(String[] args) {

		// Khởi tạo Provider container
		Provider provider = JGentle.buildProvider();
		// Lấy ra đối tượng Definition Manager
		DefinitionManager defManager = provider.getDefinitionManager();
		// Chuyển đổi thông tin annotation có trong MyClass class
		defManager.loadDefinition(MyClass.class);
		// Lấy ra thông tin definition
		Definition def = defManager.getDefinition(MyClass.class);
		for (Annotation anno : def.getAnnotations()) {
			System.out.println("annotation type: " + anno.annotationType());
		}
		System.out.println(def.getAnnotation(BeanServices.class));
		System.out.println();
		// Hiển thị thông tin annotation từ definition.
		System.out.println("Current class value: "
				+ def.getAnnotation(MYAnno.class).value());
		// Sửa đổi thông tin annotation.
		def.setValueOfAnnotation(BeanServices.class, "alias", "my new bean");
		System.out.println("New class value: "
				+ def.getAnnotation(BeanServices.class).alias());
		System.out.println("Core class value:"
				+ def.getOriginalAnnotation(BeanServices.class).alias());
		// Lấy ra thông tin annotation từ field
		System.out.println(def.getMemberDefinitionOfField("name")[0]
				.getAnnotation(MYAnno.class).value());
	}
}

@BeanServices(alias = "MYBean")
@MYAnno
class MyClass {
	@MYAnno("field value")
	public String	name;
}

@Retention(RetentionPolicy.RUNTIME)
@interface MYAnno {
	String value() default "my current bean";
}
