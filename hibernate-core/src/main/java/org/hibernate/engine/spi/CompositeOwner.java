/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.engine.spi;

/**
 * @author <a href="mailto:stale.pedersen@jboss.org">Ståle W. Pedersen</a>
 */
public interface CompositeOwner extends PrimeAmongSecondarySupertypes {
	/**
	 * @param attributeName to be added to the dirty list
	 */
	void $$_hibernate_trackChange(String attributeName);

	@Override
	default CompositeOwner asCompositeOwner() {
		return this;
	}

}
