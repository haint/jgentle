import org.jgentleframework.configure.Configurable;
import org.jgentleframework.configure.annotation.Inject;
import org.jgentleframework.configure.enums.Scope;
import org.jgentleframework.context.JGentle;
import org.jgentleframework.context.injecting.Provider;

/*
 * Copyright 2007-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Project: JGentleFrameworkExample
 */
/**
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Dec 30, 2008
 */
public class CycleTest {
	public static void main(String[] args) {

		Provider provider = JGentle.buildProvider(ConfigCycle.class);
		AA a1 = (AA) provider.getBean(AA.class);
		AA a2 = (AA) provider.getBean(AA.class);
		System.out.println(a1.b + "--" + a2.b);
		System.out.println(a1.b == a1.b.c.a.b);
	}
}

abstract class ConfigCycle implements Configurable {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.configure.Configurable#configure()
	 */
	@Override
	public void configure() {

		attach(AA.class).to(AA.class).scope(Scope.PROTOTYPE);
	}
}

class AA {
	@Inject
	public BB	b;
}

class BB {
	@Inject
	public CC	c;
}

class CC {
	@Inject
	public AA	a;
}