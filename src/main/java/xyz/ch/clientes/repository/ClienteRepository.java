package xyz.ch.clientes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import xyz.ch.clientes.model.Cliente;

public interface ClienteRepository  extends JpaRepository<Cliente,Long>{

}
