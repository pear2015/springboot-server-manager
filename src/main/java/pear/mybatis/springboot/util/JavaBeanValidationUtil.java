package pear.mybatis.springboot.util;

import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by PEAR on 2019/2/16.
 * JavaBean验证工具类
 */
public class JavaBeanValidationUtil {
    private static Validator validator;
    private JavaBeanValidationUtil() {

    }
    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    /**
     * 校验具体的JavaBean
     * 只返回校验结果
     *
     * @param t
     * @param <T>
     * @return 校验是否通过
     */
    public static <T> boolean validate(T t) {
        Set<ConstraintViolation<T>> set = validator.validate(t);

        if(!CollectionUtils.isEmpty(set)){
            List<String> msg=new ArrayList<>();
            set.stream().forEach(e->msg.add(e.getMessage()));
            //  throw new AbilityError----
        }
        return CollectionUtils.isEmpty(set);
    }
}
