package br.com.carlos.projeto.conclusao.curso.config.security;

import br.com.carlos.projeto.conclusao.curso.exceptions.UserNotFoundException;
import br.com.carlos.projeto.conclusao.curso.model.common.UserModel;
import br.com.carlos.projeto.conclusao.curso.repository.UserRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

/**
 *
 * Service that verifies whether user exists.
 *
 * @author Carlos H
 */
@Repository
@Transactional
public class ImplementsUserDetailsService implements UserDetailsService
{

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UserNotFoundException
	{
		UserModel user = userRepository.findByUsername(username);

		if (user == null)
		{
			throw new UserNotFoundException("User not found");
		}
		return new User(user.getUsername(), user.getPassword(), true, true, true, true, user.getAuthorities());
	}

}
