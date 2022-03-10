package com.ruoxi.gm.domain;

import lombok.Data;

@Data
public class PetType {
  private Long id;
  private String name;
  private String description;
  private Pet pid;
}
