INSERT INTO USUARIO(PASSWORD, USERNAME) VALUES (1, '1234', 'root');

INSERT INTO ROLE VALUES ('ADMIN');
INSERT INTO ROLE VALUES ('USER');

INSERT INTO USUARIO_ROLES VALUES ((SELECT ID FROM USUARIO WHERE USERNAME = 'root' AND ID = 1), (SELECT ROLEID FROM ROLE WHERE ROLEID = 'ADMIN'));
INSERT INTO USUARIO_ROLES VALUES ((SELECT ID FROM USUARIO WHERE USERNAME = 'root' AND ID = 1), (SELECT ROLEID FROM ROLE WHERE ROLEID = 'USER'));

/*
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema tcc
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema tcc
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `tcc` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`role
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`role` (
  `authority` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`authority`))
ENGINE = InnoDB;

USE `tcc` ;

-- -----------------------------------------------------
-- Table `tcc`.`curso`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tcc`.`curso` (
  `id` BIGINT(20) NOT NULL,
  `acronimo` VARCHAR(255) NOT NULL,
  `area` VARCHAR(255) NOT NULL,
  `nome` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tcc`.`turma`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tcc`.`turma` (
  `id` BIGINT(20) NOT NULL,
  `ano_inicial` INT(4) NOT NULL,
  `curso_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_turma_curso1_idx` (`curso_id` ASC) VISIBLE)
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tcc`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tcc`.`usuario` (
  `id` BIGINT(20) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `username` VARCHAR(80) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_863n1y3x0jalatoir4325ehal` (`username` ASC) VISIBLE)
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tcc`.`aluno`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tcc`.`aluno` (
  `id` BIGINT(20) NOT NULL,
  `ra` VARCHAR(6) NOT NULL,
  `turma_id` BIGINT(20) NOT NULL,
  `usuario_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_h6vnjfpl82o82vn1tx4g75yq3` (`ra` ASC) VISIBLE,
  INDEX `fk_aluno_turma_idx` (`turma_id` ASC) VISIBLE,
  INDEX `fk_aluno_usuario1_idx` (`usuario_id` ASC) VISIBLE)
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tcc`.`submissao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tcc`.`submissao` (
  `id` BIGINT(20) NOT NULL,
  `titulo` VARCHAR(1000) NOT NULL,
  `aluno_id` BIGINT(20) NOT NULL,
  `autorizado` BIT(1) NOT NULL,
  `momentosubmissao` DATETIME NOT NULL,
  `otsid` BIGINT(20) NULL DEFAULT NULL,
  `palavraschave` VARCHAR(1000) NULL DEFAULT NULL,
  `pathdocumentooriginal` VARCHAR(255) NOT NULL,
  `pathepub` VARCHAR(255) NULL DEFAULT NULL,
  `pathhtml` VARCHAR(255) NULL DEFAULT NULL,
  `pathpdf` VARCHAR(255) NULL DEFAULT NULL,
  `pathxml` VARCHAR(255) NULL DEFAULT NULL,
  `resumo` VARCHAR(1000) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_submissao_aluno1_idx` (`aluno_id` ASC) VISIBLE)
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tcc`.`fila_submissoes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tcc`.`fila_submissoes` (
  `id` BIGINT(20) NOT NULL,
  `enviado` BIT(1) NOT NULL,
  `submissao_submissaoid` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_filasubmissoes_submissao1_idx` (`submissao_submissaoid` ASC) VISIBLE)
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tcc`.`usuario_has_roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tcc`.`usuario_has_roles` (
  `usuario_id` BIGINT(20) NOT NULL,
  `roles_name` INT NOT NULL,
  PRIMARY KEY (`usuario_id`, `roles_name`),
  INDEX `fk_usuario_has_roles_roles1_idx` (`roles_name` ASC) VISIBLE,
  INDEX `fk_usuario_has_roles_usuario1_idx` (`usuario_id` ASC) VISIBLE)
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

*/