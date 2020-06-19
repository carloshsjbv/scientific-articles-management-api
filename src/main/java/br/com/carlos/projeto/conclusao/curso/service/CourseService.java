package br.com.carlos.projeto.conclusao.curso.service;

import br.com.carlos.projeto.conclusao.curso.model.common.CourseModel;
import br.com.carlos.projeto.conclusao.curso.model.dtos.CourseDTO;
import br.com.carlos.projeto.conclusao.curso.repository.CourseRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Business logic for courses crud.
 *
 * @author Carlos H
 */
@Service
public class CourseService
{

	@Autowired
	private CourseRepository courseRepository;

	public List<CourseModel> findAll()
	{

		List<CourseModel> cursos = (List<CourseModel>) courseRepository.findAll();
		return cursos;
	}

	public CourseModel findById(Long id) throws EntityNotFoundException
	{
		return courseRepository.findById(id).get();
	}

	public CourseModel findByAcronym(String acronym) throws EntityNotFoundException
	{
		return courseRepository.findByAcronymIgnoreCase(acronym);
	}

	public CourseModel save(CourseDTO course)
	{
		return courseRepository.save(course.transformToObject());
	}

	public CourseModel alteraCurso(CourseDTO course)
	{
		CourseModel oldCourse = findByAcronym(course.getAcronym());

		oldCourse.setName(course.getName());
		oldCourse.setArea(course.getArea());
		oldCourse.setAcronym(course.getAcronym());

		return courseRepository.save(oldCourse);
	}

	public void removeCurso(Long id)
	{
		courseRepository.deleteById(id);
	}

}
