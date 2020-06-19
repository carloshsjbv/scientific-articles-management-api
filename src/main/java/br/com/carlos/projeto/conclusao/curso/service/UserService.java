package br.com.carlos.projeto.conclusao.curso.service;

import br.com.carlos.projeto.conclusao.curso.model.common.UserModel;
import br.com.carlos.projeto.conclusao.curso.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User service.
 *
 * @author Carlos H
 */
@Service
public class UserService
{

	@Autowired
	private UserRepository userRepository;

	public UserModel findByEmail(String email)
	{
		return userRepository.findByUsername(email);
	}

}
