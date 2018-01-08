package hu.baks.jee.logger.resolver;

import java.util.Map;

import javax.el.BeanNameELResolver;
import javax.el.CompositeELResolver;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.el.FunctionMapper;
import javax.el.StandardELContext;
import javax.el.ValueExpression;
import javax.el.VariableMapper;
import javax.faces.context.FacesContext;

import hu.baks.jee.util.StringUtil;

public class ELContextResolver implements PatternResolver {

	@Override
	public String getValue(String pattern, Map<String, Object> beans) {
		if (StringUtil.isBlank(pattern)) {
            return "";
        }
		ExpressionFactory expressionFactory;
		if (FacesContext.getCurrentInstance() == null || FacesContext.getCurrentInstance().getApplication() == null) {
			expressionFactory = ExpressionFactory.newInstance();
		} else {
			expressionFactory = FacesContext.getCurrentInstance().getApplication().getExpressionFactory();
		}
		ELContext baseContext;
		if (FacesContext.getCurrentInstance() == null) {
			baseContext = new StandardELContext(expressionFactory);
		} else {
			baseContext = FacesContext.getCurrentInstance().getELContext();
		}
		ELContext ctx = new ELContextWrapper(baseContext, beans);
        ValueExpression exp = expressionFactory.createValueExpression(ctx, pattern, Object.class);
        return StringUtil.toString(exp.getValue(ctx), null);
	}

	private static class ELContextWrapper extends ELContext {
		private final ELContext base;
		private final CompositeELResolver resolver;

		private ELContextWrapper(ELContext base, Map<String, Object> beans) {
			this.base = base;
			resolver = new CompositeELResolver();
			resolver.add(new BeanNameELResolver(new MapBasedBeanResolver(beans)));
			resolver.add(base.getELResolver());
		}

		@Override
		public ELResolver getELResolver() {
			return resolver;
		}

		@Override
		public FunctionMapper getFunctionMapper() {
			return base.getFunctionMapper();
		}

		@Override
		public VariableMapper getVariableMapper() {
			return base.getVariableMapper();
		}
	}

}
