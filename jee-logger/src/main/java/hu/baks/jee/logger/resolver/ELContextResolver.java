package hu.baks.jee.logger.resolver;

import java.util.Map;

import javax.el.BeanNameELResolver;
import javax.el.CompositeELResolver;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.el.FunctionMapper;
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
		ELContext ctx = new ELContextWrapper(FacesContext.getCurrentInstance().getELContext(), beans);
        ExpressionFactory expressionFactory = FacesContext.getCurrentInstance().getApplication().getExpressionFactory();
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
