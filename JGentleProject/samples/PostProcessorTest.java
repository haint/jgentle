import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.jgentleframework.configure.Configurable;
import org.jgentleframework.configure.enums.Scope;
import org.jgentleframework.context.JGentle;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.core.handling.DefinitionManager;
import org.jgentleframework.core.reflection.AnnotationBeanException;
import org.jgentleframework.core.reflection.annohandler.AnnotationHandler;
import org.jgentleframework.core.reflection.annohandler.AnnotationPostProcessor;
import org.jgentleframework.core.reflection.metadata.AnnoMeta;
import org.jgentleframework.utils.ReflectUtils;

/**
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jan 12, 2008
 */
public class PostProcessorTest {
	public static void main(String[] args) {

		Provider injector = JGentle.buildProvider(ConfigPost.class);
		DefinitionManager dm = injector.getDefinitionManager();
		// dm.addAnnotationBeanProcessor(TestAnno.class, new MyAPP());
		dm.loadDefinition(TestPost.class);
	}
}

class MyAHR implements AnnotationHandler<TestAnno> {
	@Override
	public AnnoMeta handleVisit(TestAnno annotation, AnnoMeta annoMeta,
			DefinitionManager defManager) throws Exception {

		if (annotation.value() == false) {
			return ReflectUtils.buildAnnoMeta(annotation, annoMeta, defManager);
		}
		else {
			// Thực thi diễn dịch
			return null;
		}
	}
}

class MyAPP implements AnnotationPostProcessor<TestAnno>,
		AnnotationHandler<TestAnno> {
	@Override
	public void after(TestAnno anno, AnnoMeta parents, AnnoMeta annoMeta,
			Annotation[] listAnno, Object objConfig)
			throws AnnotationBeanException {

		System.out.println(anno + "is completed.");
		System.out.println(objConfig + " is annotated instance.");
	}

	@Override
	public void before(TestAnno anno, AnnoMeta parents, Annotation[] listAnno,
			Object objConfig) throws AnnotationBeanException {

		System.out.println(anno + "is begun.");
	}

	@Override
	public void catchException(Exception ex, TestAnno anno, AnnoMeta parents,
			AnnoMeta annoMeta, Annotation[] listAnno, Object objConfig)
			throws AnnotationBeanException {

	}

	@Override
	public AnnoMeta handleVisit(TestAnno annotation, AnnoMeta annoMeta,
			DefinitionManager defManager) throws Exception {

		// TODO Auto-generated method stub
		return null;
	}
}

@TestAnno
class TestPost {
	String	name	= "asd";
}

abstract class ConfigPost implements Configurable {
	@Override
	public void configure() {

		// addAnnotationBeanProcessor(TestAnno.class, MyAPP.class);
		// MyAPP myApp = new MyAPP();
		// addAnnotationBeanProcessor(TestAnno.class, myApp);
		bind().in(MyAPP.class).id("myAPP").scope(Scope.SINGLETON);
	}
}

@Retention(RetentionPolicy.RUNTIME)
@interface TestAnno {
	boolean value() default true;
}