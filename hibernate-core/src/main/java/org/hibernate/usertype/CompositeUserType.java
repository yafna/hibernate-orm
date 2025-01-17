/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.usertype;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.Incubating;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metamodel.spi.EmbeddableInstantiator;
import org.hibernate.metamodel.spi.ValueAccess;

/**
 * This interface should be implemented by user-defined custom types
 * that have persistent attributes and can be thought of as something
 * more like an {@linkplain jakarta.persistence.Embeddable embeddable}
 * object. However, these persistent "attributes" need not necessarily
 * correspond directly to Java fields or properties.
 * <p>
 * A value type managed by a {@code CompositeUserType} may be used in
 * almost every way that a regular embeddable type may be used. It may
 * even contain many-to-one associations.
 * <p>
 * To "map" the attributes of a composite custom type, each
 * {@code CompositeUserType} provides a {@linkplain #embeddable()
 * regular embeddable class} with the same logical structure as the
 * {@linkplain #returnedClass() value type managed by the custom
 * type}.
 * <p>
 * For example, if we implement a {@code CompositeUserType} for a
 * {@code MonetaryAmount} class, we would also provide a
 * {@code MonetaryAmountEmbeddable} class with a field for each
 * logical persistent attribute of the custom type. Of course,
 * {@code MonetaryAmountEmbeddable} is never instantiated at runtime,
 * and is never referenced in any entity class. It is a source of
 * metadata only.
 * <p>
 * Properties of this embeddable class are sorted alphabetically by
 * name, and assigned an index based on this ordering.
 * <p>
 * Every implementor of {@code CompositeUserType} must be immutable
 * and must declare a public default constructor.
 * <p>
 * A custom type may be applied to an attribute of an entity either:
 * <ul>
 * <li>explicitly, using
 *     {@link org.hibernate.annotations.CompositeType @CompositeType},
 *     or
 * <li>implicitly, using
 *     {@link org.hibernate.annotations.CompositeTypeRegistration @CompositeTypeRegistration}.
 * </ul>
 *
 * @see org.hibernate.annotations.CompositeType
 * @see org.hibernate.annotations.CompositeTypeRegistration
 */
@Incubating
public interface CompositeUserType<J> extends EmbeddableInstantiator {

	/**
	 * Get the value of the property with the given index. Properties
	 * of the {@link #embeddable()} are sorted by name and assigned an
	 * index based on this ordering.
	 *
	 * @param component an instance of class mapped by this "type"
	 * @param property the property index
	 * @return the property value
	 */
	Object getPropertyValue(J component, int property) throws HibernateException;

	@Override
	J instantiate(ValueAccess values, SessionFactoryImplementor sessionFactory);

	/**
	 * The class that represents the embeddable mapping of the type.
	 */
	Class<?> embeddable();

	/**
	 * The class returned by {@code instantiate()}.
	 */
	Class<J> returnedClass();

	/**
	 * Compare two instances of the class mapped by this type for persistence "equality".
	 * Equality of the persistent state.
	 */
	boolean equals(J x, J y);

	/**
	 * Get a hashcode for the instance, consistent with persistence "equality"
	 */
	int hashCode(J x);

	/**
	 * Return a deep copy of the persistent state, stopping at entities and at
	 * collections. It is not necessary to copy immutable objects, or null
	 * values, in which case it is safe to simply return the argument.
	 *
	 * @param value the object to be cloned, which may be null
	 * @return Object a copy
	 */
	J deepCopy(J value);

	/**
	 * Are objects of this type mutable?
	 *
	 * @return boolean
	 */
	boolean isMutable();

	/**
	 * Transform the object into its cacheable representation. At the very least this
	 * method should perform a deep copy if the type is mutable. That may not be enough
	 * for some implementations, however; for example, associations must be cached as
	 * identifier values. (optional operation)
	 *
	 * @param value the object to be cached
	 * @return a cacheable representation of the object
	 */
	Serializable disassemble(J value);

	/**
	 * Reconstruct an object from the cacheable representation. At the very least this
	 * method should perform a deep copy if the type is mutable. (optional operation)
	 *
	 * @param cached the object to be cached
	 * @param owner the owner of the cached object
	 * @return a reconstructed object from the cacheable representation
	 */
	J assemble(Serializable cached, Object owner);

	/**
	 * During merge, replace the existing (target) value in the entity we are merging to
	 * with a new (original) value from the detached entity we are merging. For immutable
	 * objects, or null values, it is safe to simply return the first parameter. For
	 * mutable objects, it is safe to return a copy of the first parameter. For objects
	 * with component values, it might make sense to recursively replace component values.
	 *
	 * @param detached the value from the detached entity being merged
	 * @param managed the value in the managed entity
	 *
	 * @return the value to be merged
	 */
	J replace(J detached, J managed, Object owner);

	@Override
	default boolean isInstance(Object object, SessionFactoryImplementor sessionFactory) {
		return returnedClass().isInstance( object );
	}

	@Override
	default boolean isSameClass(Object object, SessionFactoryImplementor sessionFactory) {
		return object.getClass().equals( returnedClass() );
	}
}
