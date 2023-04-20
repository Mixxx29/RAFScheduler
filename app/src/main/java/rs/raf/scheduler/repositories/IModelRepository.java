package rs.raf.scheduler.repositories;

import java.util.List;

import rs.raf.scheduler.models.Model;

public interface IModelRepository {
    List<Model> getAll();

    List<Model> getAll(String s);

    Model get(String s);

    Model create(Model model);

    boolean delete(Model model);

    boolean save(Model model);
}
