package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.AiBoxRepository;

import domain.AiBox;



@Component
@Transactional
public class StringToAiBoxConverter implements Converter<String,AiBox> {

	@Autowired
	AiBoxRepository repository;
	
	@Override
	public AiBox convert(String s) {
		AiBox res;
		int id;
		
		try {
			if(StringUtils.isEmpty(s))
				res=null;
			else{
				id = Integer.valueOf(s);
				res = repository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

}
