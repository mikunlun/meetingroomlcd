/**************************************************************************
 * copyright file="ServiceObjectSchema.java" company="Microsoft"
 *     Copyright (c) Microsoft Corporation.  All rights reserved.
 *
 * Defines the ServiceObjectSchema.java.
 **************************************************************************/
package microsoft.exchange.webservices.data;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Represents the base class for all item and folder schemas.
 */
@EditorBrowsable(state = EditorBrowsableState.Never)
public abstract class ServiceObjectSchema implements Iterable<PropertyDefinition> {

  /**
   * The lock object.
   */
  private static Object lockObject = new Object();

  /**
   * List of all schema types. If you add a new ServiceObject subclass that
   * has an associated schema, add the schema type to the list below.
   */
  private static LazyMember<List<Class<?>>> allSchemaTypes = new LazyMember<List<Class<?>>>(new ILazyMember<List<Class<?>>>() {
    public java.util.List<java.lang.Class<?>> createInstance() {
      List<Class<?>> typeList = new ArrayList<Class<?>>();
      // typeList.add()
          /*
					 * typeList.add(AppointmentSchema.class);
					 * typeList.add(CalendarResponseObjectSchema.class);
					 * typeList.add(CancelMeetingMessageSchema.class);
					 * typeList.add(ContactGroupSchema.class);
					 * typeList.add(ContactSchema.class);
					 * typeList.add(EmailMessageSchema.class);
					 * typeList.add(FolderSchema.class);
					 * typeList.add(ItemSchema.class);
					 * typeList.add(MeetingMessageSchema.class);
					 * typeList.add(MeetingRequestSchema.class);
					 * typeList.add(PostItemSchema.class);
					 * typeList.add(PostReplySchema.class);
					 * typeList.add(ResponseMessageSchema.class);
					 * typeList.add(ResponseObjectSchema.class);
					 * typeList.add(ServiceObjectSchema.class);
					 * typeList.add(SearchFolderSchema.class);
					 * typeList.add(TaskSchema.class);
					 */
      // Verify that all Schema types in the Managed API assembly
      // have been included.
					/*
					 * var missingTypes = from type in
					 * Assembly.GetExecutingAssembly().GetTypes() where
					 * type.IsSubclassOf(typeof(ServiceObjectSchema)) &&
					 * !typeList.Contains(type) select type; if
					 * (missingTypes.Count() > 0) { throw new
					 * ServiceLocalException
					 * ("SchemaTypeList does not include all 
					 * defined schema types."
					 * ); }
					 */
      return typeList;
    }

    ;
  });

  /**
   * Dictionary of all property definitions.
   */
  private static LazyMember<Map<String, PropertyDefinitionBase>> allSchemaProperties = new LazyMember<Map<String, PropertyDefinitionBase>>(new ILazyMember<Map<String, PropertyDefinitionBase>>() {
    public Map<String, PropertyDefinitionBase> createInstance() {
      Map<String, PropertyDefinitionBase> propDefDictionary = new HashMap<String, PropertyDefinitionBase>();
      for (Class<?> c : ServiceObjectSchema.allSchemaTypes.getMember()) {
        ServiceObjectSchema.addSchemaPropertiesToDictionary(c, propDefDictionary);
      }
      return propDefDictionary;
    }

    ;
  });

  /**
   * Adds schema properties to dictionary.
   *
   * @param type              Schema type.
   * @param propDefDictionary The property definition dictionary.
   */
  protected static void addSchemaPropertiesToDictionary(Class<?> type, Map<String, PropertyDefinitionBase> propDefDictionary) {
    Field[] fields = type.getDeclaredFields();
    for (Field field : fields) {
      int modifier = field.getModifiers();
      if (Modifier.isPublic(modifier) && Modifier.isStatic(modifier)) {
        Object o;
        try {
          o = field.get(null);
          if (o instanceof PropertyDefinition) {
            PropertyDefinition propertyDefinition = (PropertyDefinition) o;
            // Some property definitions descend from
            // ServiceObjectPropertyDefinition but don't have
            // a Uri, like ExtendedProperties. Ignore them.
            if (null != propertyDefinition.getUri() && !propertyDefinition.getUri().isEmpty()) {
              PropertyDefinitionBase existingPropertyDefinition;
              if (propDefDictionary.containsKey(propertyDefinition.getUri())) {
                existingPropertyDefinition = propDefDictionary.get(propertyDefinition.getUri());
                EwsUtilities.EwsAssert(existingPropertyDefinition == propertyDefinition, "Schema.allSchemaProperties." + "delegate", String.format("There are at least " +
                        "two distinct property " +
                        "definitions with the" +
                        " following URI: %s", propertyDefinition.getUri()));
              } else {
                propDefDictionary.put(propertyDefinition.getUri(), propertyDefinition);
                // The following is a "generic hack" to register
                // properties that are not public and
                // thus not returned by the above GetFields
                // call. It is currently solely used to register
                // the MeetingTimeZone property.
                List<PropertyDefinition> associatedInternalProperties = propertyDefinition.getAssociatedInternalProperties();
                for (PropertyDefinition associatedInternalProperty : associatedInternalProperties) {
                  propDefDictionary.put(associatedInternalProperty.getUri(), associatedInternalProperty);
                }

              }
            }
          }
        } catch (IllegalArgumentException e) {
          e.printStackTrace();

          // Skip the field
        } catch (IllegalAccessException e) {
          e.printStackTrace();

          // Skip the field
        }

      }
    }
  }

  /**
   * Adds the schema property names to dictionary.
   *
   * @param type                   The type.
   * @param propertyNameDictionary The property name dictionary.
   */
  protected static void addSchemaPropertyNamesToDictionary(Class<?> type, Map<PropertyDefinition, String> propertyNameDictionary) {

    Field[] fields = type.getDeclaredFields();
    for (Field field : fields) {
      int modifier = field.getModifiers();
      if (Modifier.isPublic(modifier) && Modifier.isStatic(modifier)) {
        Object o;
        try {
          o = field.get(null);
          if (o instanceof PropertyDefinition) {
            PropertyDefinition propertyDefinition = (PropertyDefinition) o;
            propertyNameDictionary.put(propertyDefinition, field.getName());
          }
        } catch (IllegalArgumentException e) {
          e.printStackTrace();

          // Skip the field
        } catch (IllegalAccessException e) {
          e.printStackTrace();

          // Skip the field
        }
      }
    }
  }

  /**
   * * Initializes a new instance.
   */
  protected ServiceObjectSchema() {
    this.registerProperties();
  }

  /**
   * Finds the property definition.
   *
   * @param uri The URI.
   * @return Property definition.
   */
  protected static PropertyDefinitionBase findPropertyDefinition(String uri) {
    return ServiceObjectSchema.allSchemaProperties.getMember().get(uri);
  }

  /**
   * Initialize schema property names.
   */
  protected static void initializeSchemaPropertyNames() {
    synchronized (lockObject) {
      for (Class<?> type : ServiceObjectSchema.allSchemaTypes.getMember()) {
        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
          int modifier = field.getModifiers();
          if (Modifier.isPublic(modifier) && Modifier.isStatic(modifier)) {
            Object o;
            try {
              o = field.get(null);
              if (o instanceof PropertyDefinition) {
                PropertyDefinition propertyDefinition = (PropertyDefinition) o;
                propertyDefinition.setName(field.getName());
              }
            } catch (IllegalArgumentException e) {
              e.printStackTrace();

              // Skip the field
            } catch (IllegalAccessException e) {
              e.printStackTrace();

              // Skip the field
            }
          }
        }
      }
    }
  }

  /**
   * Defines the ExtendedProperties property.
   */
  public static final PropertyDefinition extendedProperties = new ComplexPropertyDefinition<ExtendedPropertyCollection>(ExtendedPropertyCollection.class, XmlElementNames.ExtendedProperty, EnumSet.of(PropertyDefinitionFlags.AutoInstantiateOnRead, PropertyDefinitionFlags.ReuseInstance, PropertyDefinitionFlags.CanSet, PropertyDefinitionFlags.CanUpdate), ExchangeVersion.Exchange2007_SP1, new ICreateComplexPropertyDelegate<ExtendedPropertyCollection>() {
    public ExtendedPropertyCollection createComplexProperty() {
      return new ExtendedPropertyCollection();
    }

    ;
  });

  /**
   * The properties.
   */
  private Map<String, PropertyDefinition> properties = new HashMap<String, PropertyDefinition>();

  /**
   * The visible properties.
   */
  private List<PropertyDefinition> visibleProperties = new ArrayList<PropertyDefinition>();

  /**
   * The first class properties.
   */
  private List<PropertyDefinition> firstClassProperties = new ArrayList<PropertyDefinition>();

  /**
   * The first class summary properties.
   */
  private List<PropertyDefinition> firstClassSummaryProperties = new ArrayList<PropertyDefinition>();

  private List<IndexedPropertyDefinition> indexedProperties = new ArrayList<IndexedPropertyDefinition>();

  /**
   * Registers a schema property.
   *
   * @param property   The property to register.
   * @param isInternal Indicates whether the property is internal or should be
   *                   visible to developers.
   */
  private void registerProperty(PropertyDefinition property, boolean isInternal) {
    this.properties.put(property.getXmlElement(), property);

    if (!isInternal) {
      this.visibleProperties.add(property);
    }

    // If this property does not have to be requested explicitly, add
    // it to the list of firstClassProperties.
    if (!property.hasFlag(PropertyDefinitionFlags.MustBeExplicitlyLoaded)) {
      this.firstClassProperties.add(property);
    }

    // If this property can be found, add it to the list of
    // firstClassSummaryProperties
    if (property.hasFlag(PropertyDefinitionFlags.CanFind)) {
      this.firstClassSummaryProperties.add(property);
    }
  }

  /**
   * Registers a schema property that will be visible to developers.
   *
   * @param property The property to register.
   */
  protected void registerProperty(PropertyDefinition property) {
    this.registerProperty(property, false);
  }

  /**
   * Registers an internal schema property.
   *
   * @param property The property to register.
   */
  protected void registerInternalProperty(PropertyDefinition property) {
    this.registerProperty(property, true);
  }

  /**
   * Registers an indexed property.
   *
   * @param indexedProperty The indexed property to register.
   */
  protected void registerIndexedProperty(IndexedPropertyDefinition indexedProperty) {
    this.indexedProperties.add(indexedProperty);
  }


  /**
   * Registers properties.
   */
  protected void registerProperties() {
  }

  /**
   * * Gets the list of first class properties for this service object type.
   *
   * @return the first class properties
   */
  protected List<PropertyDefinition> getFirstClassProperties() {
    return this.firstClassProperties;
  }

  /**
   * * Gets the list of first class summary properties for this service object
   * type.
   *
   * @return the first class summary properties
   */
  protected List<PropertyDefinition> getFirstClassSummaryProperties() {
    return this.firstClassSummaryProperties;
  }

  /**
   * Tries to get property definition.
   *
   * @param xmlElementName             Name of the XML element.
   * @param propertyDefinitionOutParam The property definition.
   * @return True if property definition exists.
   */
  protected boolean tryGetPropertyDefinition(String xmlElementName, OutParam<PropertyDefinition> propertyDefinitionOutParam) {
    if (this.properties.containsKey(xmlElementName)) {
      propertyDefinitionOutParam.setParam(this.properties.get(xmlElementName));
      return true;
    } else {
      return false;
    }
  }

  /**
   * Returns an iterator over a set of elements of type T.
   *
   * @return an Iterator.
   */
  @Override
  public Iterator<PropertyDefinition> iterator() {
    return this.visibleProperties.iterator();
  }
}
