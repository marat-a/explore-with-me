package ru.practicum.mainserver.event.model;

import com.vividsolutions.jts.geom.Point;
import ru.practicum.mainserver.category.model.Category;
import ru.practicum.mainserver.common.enums.EventState;
import ru.practicum.mainserver.user.model.User;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Event.class)
public abstract class Event_ {

	public static volatile SingularAttribute<Event, String> annotation;
	public static volatile SingularAttribute<Event, User> initiator;
	public static volatile SingularAttribute<Event, String> description;
	public static volatile SingularAttribute<Event, LocalDateTime> publishedOn;
	public static volatile SingularAttribute<Event, String> title;
	public static volatile SingularAttribute<Event, Long> confirmedRequests;
	public static volatile SingularAttribute<Event, LocalDateTime> createdOn;
	public static volatile SingularAttribute<Event, Long> participantLimit;
	public static volatile SingularAttribute<Event, Boolean> paid;
	public static volatile SingularAttribute<Event, Boolean> requestModeration;
	public static volatile SingularAttribute<Event, Point> coordinates;
	public static volatile SingularAttribute<Event, Long> id;
	public static volatile SingularAttribute<Event, EventState> state;
	public static volatile SingularAttribute<Event, Category> category;
	public static volatile SingularAttribute<Event, Long> views;
	public static volatile SingularAttribute<Event, LocalDateTime> eventDate;

	public static final String ANNOTATION = "annotation";
	public static final String INITIATOR = "initiator";
	public static final String DESCRIPTION = "description";
	public static final String PUBLISHED_ON = "publishedOn";
	public static final String TITLE = "title";
	public static final String CONFIRMED_REQUESTS = "confirmedRequests";
	public static final String CREATED_ON = "createdOn";
	public static final String PARTICIPANT_LIMIT = "participantLimit";
	public static final String PAID = "paid";
	public static final String REQUEST_MODERATION = "requestModeration";
	public static final String COORDINATES = "coordinates";
	public static final String ID = "id";
	public static final String STATE = "state";
	public static final String CATEGORY = "category";
	public static final String VIEWS = "views";
	public static final String EVENT_DATE = "eventDate";

}

