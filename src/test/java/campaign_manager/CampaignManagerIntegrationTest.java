package campaign_manager;

import com.greenapper.Main;
import com.greenapper.controllers.CampaignManagerController;
import com.greenapper.models.CampaignManager;
import com.greenapper.models.User;
import com.greenapper.services.CampaignManagerService;
import com.greenapper.services.SessionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = Main.class
)
public class CampaignManagerIntegrationTest {
	@Autowired
	private CampaignManagerController campaignManagerController;

	@Autowired
	private CampaignManagerService campaignManagerService;

	@Autowired
	private SessionService sessionService;

	@Test
	public void updatePasswordLessThan6Chars() {
		final CampaignManager campaignManager = campaignManagerService.getByUsername("admin").orElse(null);
		final BindingResult bindingResult = new BeanPropertyBindingResult(campaignManager, "campaignManager");

		assertNotNull(campaignManager);

		sessionService.setSessionUser(campaignManager);

		campaignManager.setPassword("12345");

		final String ret = campaignManagerController.updatePassword(campaignManager, bindingResult);

		assertEquals(ret, CampaignManagerController.PASSWORD_UPDATE_FORM);
		assertEquals(1, bindingResult.getErrorCount());
		assertEquals("err.password.length", bindingResult.getAllErrors().get(0).getCode());
	}

	@Test
	public void updatePasswordSamePassword() {
		final CampaignManager campaignManager = campaignManagerService.getByUsername("admin").orElse(null);

		assertNotNull(campaignManager);

		sessionService.setSessionUser(campaignManager);

		final User newPwdUser = new User();
		final BindingResult bindingResult = new BeanPropertyBindingResult(newPwdUser, "user");
		newPwdUser.setPassword("testing");

		final String ret = campaignManagerController.updatePassword(newPwdUser, bindingResult);

		assertEquals(CampaignManagerController.PASSWORD_UPDATE_FORM, ret);
		assertEquals(1, bindingResult.getErrorCount());
		assertEquals("err.password.samepassword", bindingResult.getAllErrors().get(0).getCode());
	}

	@Test
	@DirtiesContext
	public void updatePasswordSuccessfully() {
		final CampaignManager campaignManager = campaignManagerService.getByUsername("admin").orElse(null);

		assertNotNull(campaignManager);

		sessionService.setSessionUser(campaignManager);

		final User newPwdUser = new User();
		final BindingResult bindingResult = new BeanPropertyBindingResult(newPwdUser, "user");
		newPwdUser.setPassword("newpassword");

		final String ret = campaignManagerController.updatePassword(newPwdUser, bindingResult);

		assertEquals(CampaignManagerController.PASSWORD_UPDATE_SUCCESS_REDIRECT, ret);
		assertFalse(bindingResult.hasErrors());
	}
}
