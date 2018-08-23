package com.lister.itms.utils;

import org.joda.time.DateTime;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对象COPY
 *
 * @class com.xianglin.xlnodecore.common.util.DTOUtils
 * @date 2015年9月17日 下午2:18:46
 */
public class DTOUtils {

	public static final Logger log = LoggerFactory.getLogger(DTOUtils.class);

	public static final ModelMapper INSTANCE = new ModelMapper();

	static {
		INSTANCE.addConverter(new DateTimeToDateConverter());
		INSTANCE.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	public static <S, T> T map(S source, Class<T> targetClass) {

		return INSTANCE.map(source, targetClass);
	}

	public static <S, T> void map(S source, T dist) {

		INSTANCE.map(source, dist);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <S, T> List<T> map(List<S> source, Class<T> targetClass) {

		List list = new ArrayList(source.size());
		for (Object obj : source) {
			Object target = INSTANCE.map(obj, targetClass);
			list.add(target);
		}
		return list;
	}

	public static Map<String, Object> beanToMap(Object source) throws Exception {
		if(source == null){
			throw new Exception("beanToMap source bean is null");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] fds = source.getClass().getDeclaredFields();
		for(Field f:fds){
			f.setAccessible(true);
			map.put(f.getName(), f.get(source));
		}
		return map;
	}

	private static class DateTimeToDateConverter implements Converter<DateTime, Date> {
		@Override
		public Date convert(MappingContext<DateTime, Date> context) {

			DateTime source = context.getSource();
			if (source != null) {
				return source.toDate();
			}
			return null;
		}
	}
	
}
