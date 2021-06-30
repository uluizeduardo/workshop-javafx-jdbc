package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDao {

	void insert(Department obj);//Operação para inserir no banco de dados o obj do tipo Department
	void update(Department obj);//Operação para atualizar no banco de dados o obj do tipo Department
	void deleteById(Integer id);//Operação para deletar no banco de dados um objeto pelo id
	Department findById(Integer id);//Operação que pegar um id e consulta no banco de dados
	List<Department> findAll();//Operação para retornar todos os dados do Department
}
