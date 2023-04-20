package rs.raf.scheduler.models;

import java.util.List;

import rs.raf.scheduler.repositories.IModelRepository;

public abstract class Model{

    private final IModelRepository repository;

    public Model(IModelRepository repository) {
        this.repository = repository;
    }

    public List<Model> getAll() {
        return repository.getAll();
    }

    public List<Model> getAll(String s) {
        return repository.getAll(s);
    }

    public Model get(String s) {
        return repository.get(s);
    }

    public void create(Model model) {
        repository.create(model);
    }

    public boolean save() {
        return repository.save(this);
    }
}
