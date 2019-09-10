package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.AiBox;

@Component
@Transactional
public class AiBoxToStringConverter implements Converter<AiBox, String> {

	@Override
	public String convert(AiBox o) {
		String res;
		
		if(o == null)
			res = null;
		else
			res= String.valueOf(o.getId());
		
		return res;
	}
}
