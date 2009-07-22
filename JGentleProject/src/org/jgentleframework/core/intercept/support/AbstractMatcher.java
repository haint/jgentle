/*
 * Copyright 2007-2009 the original author or authors.
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
 * Project: JGentleFramework
 */
package org.jgentleframework.core.intercept.support;

import java.util.ArrayList;
import java.util.List;

import org.jgentleframework.core.intercept.InterceptionException;
import org.jgentleframework.utils.Assertor;
import org.jgentleframework.utils.ReflectUtils;

/**
 * The Class AbstractMatcher.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 2, 2008
 * @see Matcher
 */
public abstract class AbstractMatcher<T> implements Matcher<T> {
	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.intercept.Matcher#and(org.jgentleframework.
	 * core.intercept.Matcher)
	 */
	public Matcher<T> and(final Matcher<T> other) {

		return new AndMatcher<T>(this, other);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.intercept.Matcher#or(org.jgentleframework.core
	 * .intercept.Matcher)
	 */
	public Matcher<T> or(Matcher<T> other) {

		return new OrMatcher<T>(this, other);
	}

	/**
	 * The Class AndMatcher.
	 */
	static class AndMatcher<T> extends AndOrMatcher<T> {
		/**
		 * Instantiates a new and matcher.
		 * 
		 * @param a
		 *            the matcher a
		 * @param b
		 *            the matcher b
		 */
		@SuppressWarnings("unchecked")
		public AndMatcher(Matcher<T> a, Matcher<T> b) {

			Assertor.notNull(a, "The given matcher must not be null!");
			Assertor.notNull(b, "The given matcher must not be null!");
			boolean result = ReflectUtils.isCast(a, AndOrMatcher.class) ? this.matcherList
					.addAll(((AndOrMatcher) a).getMatcherList())
					: this.matcherList.add(a);
			result = ReflectUtils.isCast(b, AndOrMatcher.class) ? this.matcherList
					.addAll(((AndOrMatcher) b).getMatcherList())
					: this.matcherList.add(b) && result;
			if (result == false) {
				throw new InterceptionException(
						"Could not instantiate AndMatcher instance !");
			}
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * org.jgentleframework.core.intercept.Matcher#matches(java.lang.Object)
		 */
		public boolean matches(T t) {

			Boolean result = true;
			for (int i = 0; i < matcherList.size(); i++) {
				if (i + 1 < matcherList.size()) {
					result = matcherList.get(i).matches(t)
							&& matcherList.get(i + 1).matches(t);
				}
			}
			return result;
		}
	}

	/**
	 * Gets the super matcher.
	 * 
	 * @param matcher
	 *            the matcher
	 * @param matcherList
	 *            the matcher list
	 */
	@SuppressWarnings("unchecked")
	public static <T> void getSuperMatcher(Matcher<T> matcher,
			List<Matcher<T>> matcherList) {

		Assertor.notNull(matcherList);
		Assertor.notNull(matcher);
		if (ReflectUtils.isCast(matcher, AndOrMatcher.class)) {
			ArrayList<Matcher<T>> list = (((AndOrMatcher) matcher)
					.getMatcherList());
			for (Matcher<T> obj : list) {
				if (!matcherList.contains(matcher)) {
					matcherList.add(obj);
				}
			}
			for (Matcher<T> obj : matcherList) {
				if (ReflectUtils.isCast(obj, AndOrMatcher.class)) {
					getSuperMatcher(obj, matcherList);
				}
			}
		}
		else {
			if (!matcherList.contains(matcher)) {
				matcherList.add(matcher);
			}
		}
	}

	/**
	 * The Class OrMatcher.
	 */
	static class OrMatcher<T> extends AndOrMatcher<T> {
		/**
		 * Instantiates a new or matcher.
		 * 
		 * @param a
		 *            the matcher a
		 * @param b
		 *            the matcher b
		 */
		@SuppressWarnings("unchecked")
		public OrMatcher(Matcher<T> a, Matcher<T> b) {

			Assertor.notNull(a, "The given matcher must not be null!");
			Assertor.notNull(b, "The given matcher must not be null!");
			boolean result = ReflectUtils.isCast(a, AndOrMatcher.class) ? this.matcherList
					.addAll(((AndOrMatcher) a).getMatcherList())
					: this.matcherList.add(a);
			result = ReflectUtils.isCast(b, AndOrMatcher.class) ? this.matcherList
					.addAll(((AndOrMatcher) b).getMatcherList())
					: this.matcherList.add(b) && result;
			if (result == false) {
				throw new InterceptionException(
						"Could not instantiate OrMatcher instance !");
			}
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * org.jgentleframework.core.intercept.Matcher#matches(java.lang.Object)
		 */
		public boolean matches(T t) {

			Boolean result = true;
			for (int i = 0; i < matcherList.size(); i++) {
				if (i + 1 < matcherList.size()) {
					result = matcherList.get(i).matches(t)
							|| matcherList.get(i + 1).matches(t);
				}
			}
			return result;
		}
	}

	/**
	 * The Interface AndOrMatcher.
	 */
	static abstract class AndOrMatcher<T> extends AbstractMatcher<T> {
		/** The matcher list. */
		ArrayList<Matcher<T>>	matcherList	= new ArrayList<Matcher<T>>();

		/**
		 * Gets the matcher list.
		 * 
		 * @return the matcherList
		 */
		public ArrayList<Matcher<T>> getMatcherList() {

			return matcherList;
		}
	}
}
