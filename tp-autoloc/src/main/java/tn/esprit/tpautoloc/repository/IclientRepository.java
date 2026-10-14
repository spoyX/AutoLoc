package tn.esprit.tpautoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.tpautoloc.domain.Client;

public interface IclientRepository extends JpaRepository<Client, Long> {
}
