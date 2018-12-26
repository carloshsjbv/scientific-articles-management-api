package br.com.carlos.projeto.conclusao.curso.service;

import br.com.carlos.projeto.conclusao.curso.model.dtos.SubmissaoDTO;
import br.com.carlos.projeto.conclusao.curso.model.entity.AlunoEntity;
import br.com.carlos.projeto.conclusao.curso.model.entity.FilaSubmissoes;
import br.com.carlos.projeto.conclusao.curso.model.entity.SubmissaoEntity;
import br.com.carlos.projeto.conclusao.curso.model.entity.UsuarioEntity;
import br.com.carlos.projeto.conclusao.curso.repository.CursoRepository;
import br.com.carlos.projeto.conclusao.curso.repository.FilaSubmissoesRepository;
import br.com.carlos.projeto.conclusao.curso.repository.SubmissaoRepository;
import br.com.carlos.projeto.conclusao.curso.components.ManipuladorDeArquivos;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Carlos H
 */
@Service
public class SubmissaoService {

    @Autowired
    private SubmissaoRepository submissaoRepository;

    @Autowired
    private FilaSubmissoesRepository filaSubmissoesRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private ManipuladorDeArquivos fileSaver;

    public long countAll() {
        return submissaoRepository.count();
    }

    public SubmissaoEntity findById(Long id) throws EntityNotFoundException, IOException {
        SubmissaoEntity submissao = submissaoRepository.findById(id).get();
        submissao.setPathHTML(System.getProperty("user.dir") + System.getProperty("file.separator") + submissao.getPathHTML() + submissao.getPathHTML());
        return submissao;
    }

    public List<SubmissaoEntity> findAll() throws EntityNotFoundException {
        List<SubmissaoEntity> submissoes = manipulaPath(submissaoRepository.findAll());
        return submissoes;
    }

    public List<SubmissaoEntity> findAllByCourse(String acronimo) throws EntityNotFoundException {
        List<SubmissaoEntity> submissoes = manipulaPath(submissaoRepository.findAllByAluno_Turma_Curso(cursoRepository.findByAcronimoIgnoreCase(acronimo)));
        return submissoes;
    }

    public List<SubmissaoEntity> findAllByTurmaAnoCurso(int anoInicial) throws EntityNotFoundException {
        return (List<SubmissaoEntity>) submissaoRepository.findAllByAluno_TurmaAnoInicial(anoInicial);
    }

    public List<SubmissaoEntity> findAllByAcronym() throws EntityNotFoundException {
        return (List<SubmissaoEntity>) submissaoRepository.findAll();
    }

    public SubmissaoEntity save(SubmissaoDTO submissao) throws SQLException, IOException {

        UsuarioEntity usuario = usuarioService.findByEmail(submissao.getEmail());

        AlunoEntity aluno = alunoService.findById(usuario.getId());

        if (aluno != null) {

            if (aluno.getTurma() != null) {

                try {
                    String[] paths = fileSaver.getPaths(aluno);

                    String path = fileSaver.salvaArquivo(paths, submissao.getArquivo());

                    SubmissaoEntity submissaoEntity = new SubmissaoEntity();

                    submissaoEntity.setTitulo(submissao.getTitulo());
                    submissaoEntity.setResumo(submissao.getResumo());
                    submissaoEntity.setPalavrasChave(submissao.getKeywords());

                    submissaoEntity.setAluno(aluno);
                    submissaoEntity.setAutorizado(true);
                    submissaoEntity.setMomentoSubmissao(Calendar.getInstance(Locale.getDefault()));
                    submissaoEntity.setOtsId(0);
                    submissaoEntity.setPathDocumentoOriginal(path);

                    try {

                        // Salva submissão
                        submissaoRepository.save(submissaoEntity);

                        // Cria instancia da fila de submissões, e adiciona a submissão
                        // atual a ela.
                        FilaSubmissoes fila = new FilaSubmissoes();
                        fila.setSubmissaoEntity(submissaoEntity);
                        fila.setEnviado(false);

                        // Salva submissão na fila
                        filaSubmissoesRepository.save(fila);

                        return submissaoEntity;
                    } catch (Exception e) {
                        throw new SQLException(e);
                    }

                } catch (SQLException e) {
                    throw e;
                }
            }

        }

        return null;
    }

    private List<SubmissaoEntity> manipulaPath(List<SubmissaoEntity> list) {
        List<SubmissaoEntity> lista = new ArrayList<>();

        String userDir = System.getProperty("user.dir");

        for (SubmissaoEntity submissaoEntity : list) {
            submissaoEntity.setPathDocumentoOriginal(userDir + System.getProperty("file.separator") + submissaoEntity.getPathDocumentoOriginal());
            submissaoEntity.setPathPDF(submissaoEntity.getPathPDF() != null ? userDir + System.getProperty("file.separator") + submissaoEntity.getPathPDF() : null);
            submissaoEntity.setPathXML(submissaoEntity.getPathXML() != null ? userDir + System.getProperty("file.separator") + submissaoEntity.getPathXML() : null);
            submissaoEntity.setPathEpub(submissaoEntity.getPathEpub() != null ? userDir + System.getProperty("file.separator") + submissaoEntity.getPathEpub() : null);
            submissaoEntity.setPathHTML(submissaoEntity.getPathHTML() != null ? userDir + System.getProperty("file.separator") + submissaoEntity.getPathHTML() : null);
            lista.add(submissaoEntity);
        }
        return lista;

    }
}
