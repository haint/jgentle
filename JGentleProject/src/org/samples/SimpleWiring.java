package org.samples;

import java.lang.reflect.Field;
import java.util.Map;

import org.jgentleframework.configure.Configurable;
import org.jgentleframework.configure.annotation.Inject;
import org.jgentleframework.configure.enums.Scope;
import org.jgentleframework.context.JGentle;
import org.jgentleframework.context.beans.Filter;
import org.jgentleframework.context.injecting.Provider;

public class SimpleWiring {
	public static void main(String[] args) {

		Provider provider = JGentle.buildProvider(ConfigModule.class);
		KnightElf knight = (KnightElf) provider.getBean(KnightElf.class);
		KnightElf knightBind = (KnightElf) provider
				.getBeanBoundToDefinition("bean1");
		knight.showInfo();
		knightBind.showInfo();
		System.out.println(knight.type);
		// Definition def =
		// provider.getDefinitionManager().getDefinition("bean1");
		// System.out.println(def.getMemberDefinitionOfField("type")[0]
		// .getAnnotation(Inject.class).value());
	}
}

abstract class ConfigModule implements Configurable {
	@Override
	public void configure() {

		attach(KnightType.class).to(SwordSinger.class).scope(Scope.SINGLETON);
		bind(
				new Object[][] { { "type", refMapping(TempleKnight.class) },
						{ "level", 55 }, }).in(KnightElf.class).id("bean1");
		attachConstant("level").to(45);
	}
}

class KnightElf implements Filter {
	@Inject(invocation = true)
	public KnightType	type;

	@Inject("level")
	int					level	= 0;

	public KnightType getType() {

		return type;
	}

	public int getLevel() {

		return level;
	}

	public void showInfo() {

		System.out.println("Class type: " + this.getType().getName());
		System.out.println("Level: " + this.getLevel() + " <---");
		System.out.println("Defence: " + this.getType().getDef());
		System.out.println("Strength: " + this.getType().getStr());
		System.out.println();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.beans.FactoryFilter#filters(java.util.Map)
	 */
	@Override
	public void filters(Map<Field, Object> map) {

		for (Field field : map.keySet()) {
			if (field.getName().equals("level")
					&& ((Integer) map.get(field)).intValue() == 0) {
				this.level = 60;
			}
		}
	}
}

interface KnightType {
	public String getName();

	public int getDef();

	public int getStr();
}

class TempleKnight implements KnightType {
	String	name	= "TempleKnight";

	int		def		= 1000;

	int		str		= 400;

	public String getName() {

		return name;
	}

	public int getDef() {

		return def;
	}

	public int getStr() {

		return str;
	}
}

class SwordSinger implements KnightType {
	String	name	= "SwordSinger";

	int		def		= 800;

	int		str		= 400;

	public String getName() {

		return name;
	}

	public int getDef() {

		return def;
	}

	public int getStr() {

		return str;
	}
}
