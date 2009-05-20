package org.samples;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.aopalliance.reflect.Metadata;
import org.jgentleframework.context.JGentle;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.core.handling.DefinitionManager;
import org.jgentleframework.core.reflection.metadata.AnnoMeta;
import org.jgentleframework.core.reflection.metadata.Definition;

/**
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jan 5, 2008
 */
public class AnnoMetaTest {
	public static void main(String[] args) {

		// Khởi tạo Provider container
		Provider injector = JGentle.buildServiceProvider();
		// Lấy ra đối tượng Definition Manager
		DefinitionManager defManager = injector.getDefinitionManager();
		// Chuyển đổi thông tin annotation có trong MyClass class
		defManager.loadDefinition(Client.class);
		// Lấy ra thông tin definition
		Definition def = defManager.getDefinition(Client.class);
		AnnoMeta root = def.getAnnoMeta();
		AnnoMeta annoMeta = (AnnoMeta) root.getMetadata(MyAnnotation.class);
		Metadata metadata = annoMeta.getMetadata("name");
		System.out.println(metadata.getValue().toString());
	}
}

@MyAnnotation
class Client {
	@MYAnno
	public String	name;
}

@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation {
	String name() default "JGentle";
}
