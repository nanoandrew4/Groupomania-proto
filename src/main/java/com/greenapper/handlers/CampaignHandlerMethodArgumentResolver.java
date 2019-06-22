package com.greenapper.handlers;

import com.greenapper.models.campaigns.Campaign;
import com.greenapper.models.campaigns.OfferCampaign;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.ModelFactory;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.ServletRequest;
import java.util.Map;

public class CampaignHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		return methodParameter.getParameterType().equals(Campaign.class);
	}

	@Override
	public Object resolveArgument(MethodParameter methodParameter,
								  ModelAndViewContainer modelAndViewContainer,
								  NativeWebRequest nativeWebRequest,
								  WebDataBinderFactory webDataBinderFactory) throws Exception {
		final String campaignType = nativeWebRequest.getParameter("type");

		final String name = ModelFactory.getNameForParameter(methodParameter);

		final Campaign campaign;
		if (campaignType != null) {
			final String campaignClassName = campaignType.charAt(0) + campaignType.substring(1).toLowerCase() + "Campaign";
			campaign = (Campaign) Class.forName(Campaign.class.getPackage().getName() + "." + campaignClassName).getConstructor().newInstance();
		} else
			campaign = new OfferCampaign();

		final WebDataBinder binder = webDataBinderFactory.createBinder(nativeWebRequest, campaign, name);
		bindParameters(nativeWebRequest, binder);
		final BindingResult bindingResult = binder.getBindingResult();

		Map<String, Object> bindingResultModel = bindingResult.getModel();
		modelAndViewContainer.removeAttributes(bindingResultModel);
		modelAndViewContainer.addAllAttributes(bindingResultModel);
		return campaign;
	}

	// Copied from ServletModelAttributeMethodProcessor#bindRequestParameters(WebDataBinder, NativeWebRequest)
	private void bindParameters(final NativeWebRequest nativeWebRequest, final WebDataBinder binder) {
		ServletRequest servletRequest = nativeWebRequest.getNativeRequest(ServletRequest.class);
		Assert.state(servletRequest != null, "No ServletRequest");
		ServletRequestDataBinder servletBinder = (ServletRequestDataBinder) binder;
		servletBinder.bind(servletRequest);
	}
}