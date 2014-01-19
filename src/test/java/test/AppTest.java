package test; 

import generic.domain.Role;
import generic.domain.User;
import generic.service.GenericService;
import generic.service.RoleService;
import generic.service.UserService;

import java.io.IOException;
import java.util.List;

import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SuppressWarnings({"unchecked", "rawtypes"})
@ContextConfiguration({"/applicationContext.xml"})
public class AppTest   
{
	protected final Logger LOGGER = LoggerFactory.getLogger( getClass() );
	
	@Autowired
	private GenericService genericService;
	@Autowired
	private UserService userService;	
	@Autowired
	private RoleService roleService;	
	
	@Before
	public void setUp() throws IOException
	{
	}
		
	@Test
	public void testGenericService()
	{		
		LocalDateTime now = new LocalDateTime();
		
		List<User> users = genericService.getAll(User.class);
		Assert.assertTrue( users.size() > 0 );
		for ( User user : users ) {
			user.setCreatedBy("script"); user.setCreatedDate(now);
			user.setModifiedBy("script"); user.setModifiedDate(now);
			user.setDeleted(false);
			genericService.saveOrUpdate(user);
		}
		
		Role role = (Role) genericService.getEntityById(-1l, Role.class);
		Assert.assertNotNull(role);
		List<Role> roles = genericService.getAll(Role.class);
		for (Role r : roles) {
			r.setCreatedBy("script"); r.setCreatedDate(now);
			r.setModifiedBy("script"); r.setModifiedDate(now);
			r.setDeleted(false);
			genericService.saveOrUpdate(r);
		}
	}
	
	@Test
	public void testUserService() {
		User user = userService.getEntityById(-1l);
		Assert.assertNotNull(user);
	}
	
	@Test
	public void testRoleService() {
		Role role = roleService.getEntityById(-2l);
		Assert.assertNotNull(role);
	}	
}
