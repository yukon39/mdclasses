/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2020
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 *
 * MDClasses is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * MDClasses is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with MDClasses.
 */
package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.AttributeType;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOModule;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOReference;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import com.github._1c_syntax.mdclasses.utils.MDOFactory;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Базовый класс для тестов объектов метаданных
 */
abstract public class AbstractMDOTest {

  /**
   * Каталог исходников конфигурации в формате EDT
   */
  private static final String SRC_EDT = "src/test/resources/metadata/edt/src";

  /**
   * Каталог исходников конфигурации в формате EDT для англоязычной конфигурации
   */
  private static final String SRC_EDT_EN = "src/test/resources/metadata/edt_en/src";

  /**
   * Каталог исходников конфигурации в формате Конфигуратора
   */
  private static final String SRC_DESIGNER = "src/test/resources/metadata/original";

  /**
   * Тип тестируемого объекта метаданных
   */
  private final MDOType mdoType;

  AbstractMDOTest(MDOType type) {
    mdoType = type;
  }

  /**
   * Обязательный тест в формате EDT
   */
  @Test
  abstract protected void testEDT();

  /**
   * Обязательный тест в формате Конфигуратора
   */
  @Test
  abstract protected void testDesigner();

  /**
   * Возвращает объект метаданных по файлу описания в формате EDT
   *
   * @param partPath путь к файлу описания объекта
   * @return прочитанный объект
   */
  protected MDObjectBase getMDObjectEDT(String partPath) {
    var mdo = MDOFactory.readMDObject(ConfigurationSource.EDT, mdoType, Paths.get(SRC_EDT, partPath));
    assertThat(mdo).isPresent();
    return mdo.get();
  }

  /**
   * Возвращает объект метаданных по файлу описания в формате EDT для англоязычной конфигурации
   *
   * @param partPath путь к файлу описания объекта
   * @return прочитанный объект
   */
  protected MDObjectBase getMDObjectEDTEn(String partPath) {
    var mdo = MDOFactory.readMDObject(ConfigurationSource.EDT, mdoType, Paths.get(SRC_EDT_EN, partPath));
    assertThat(mdo).isPresent();
    return mdo.get();
  }

  /**
   * Возвращает объект метаданных по файлу описания в формате Конфигуратора
   *
   * @param partPath путь к файлу описания объекта
   * @return прочитанный объект
   */
  protected MDObjectBase getMDObjectDesigner(String partPath) {
    var mdo = MDOFactory.readMDObject(ConfigurationSource.DESIGNER, mdoType, getMDOPathDesigner(partPath));
    assertThat(mdo).isPresent();
    return mdo.get();
  }

  /**
   * Проверяет корректность чтения базовых полей
   */
  protected void checkBaseField(MDObjectBase mdo, Class<?> clazz, String name, String uuid) {
    assertThat(mdo)
      .isInstanceOf(clazz).extracting(MDObjectBase::getName)
      .isEqualTo(name);

    assertThat(mdo.getType()).isEqualTo(mdoType);

    assertThat(mdo.getUuid()).isEqualTo(uuid);
    assertThat(mdo.getMdoReference())
      .isNotNull().extracting(MDOReference::getType)
      .isEqualTo(mdoType);
    assertThat(mdo.getMdoReference().getMdoRef())
      .isEqualTo(mdoType.getName() + "." + name);

  }

  /**
   * Выполнение проверки дочерних форм - пусто
   */
  protected void checkForms(MDObjectBase mdo) {
    assertThat(mdo).isInstanceOf(MDObjectComplex.class);
    var mdoComplex = (MDObjectComplex) mdo;
    assertThat(mdoComplex.getForms()).hasSize(0);
  }

  /**
   * Выполнение проверки дочерних форм
   */
  protected void checkForms(MDObjectBase mdo, int count, String parentName, String... names) {
    assertThat(mdo).isInstanceOf(MDObjectComplex.class);
    var mdoComplex = (MDObjectComplex) mdo;
    var children = mdoComplex.getForms();
    assertThat(children).hasSize(count);
    assertThat(children).allMatch(MDObjectBase.class::isInstance);
    assertThat(children).allMatch(child -> List.of(names).contains(child.getName()));
    children.forEach(child -> checkChild(parentName, MDOType.FORM, ModuleType.FormModule, child));
  }

  /**
   * Выполнение проверки дочерних макетов - пусто
   */
  protected void checkTemplates(MDObjectBase mdo) {
    assertThat(mdo).isInstanceOf(MDObjectComplex.class);
    var mdoComplex = (MDObjectComplex) mdo;
    assertThat(mdoComplex.getTemplates()).hasSize(0);
  }

  /**
   * Выполнение проверки дочерних макетов
   */
  protected void checkTemplates(MDObjectBase mdo, int count, String parentName, String... names) {
    assertThat(mdo).isInstanceOf(MDObjectComplex.class);
    var mdoComplex = (MDObjectComplex) mdo;
    var children = mdoComplex.getTemplates();
    assertThat(children).hasSize(count);
    assertThat(children).allMatch(MDObjectBase.class::isInstance);
    assertThat(children).allMatch(child -> List.of(names).contains(child.getName()));
    children.forEach(child -> checkChild(parentName, MDOType.TEMPLATE, ModuleType.Unknown, child));
  }

  /**
   * Выполнение проверки дочерних команд - пусто
   */
  protected void checkCommands(MDObjectBase mdo) {
    assertThat(mdo).isInstanceOf(MDObjectComplex.class);
    var mdoComplex = (MDObjectComplex) mdo;
    assertThat(mdoComplex.getCommands()).hasSize(0);
  }

  /**
   * Выполнение проверки дочерних команд
   */
  protected void checkCommands(MDObjectBase mdo, int count, String parentName, String... names) {
    assertThat(mdo).isInstanceOf(MDObjectComplex.class);
    var mdoComplex = (MDObjectComplex) mdo;
    var children = mdoComplex.getCommands();
    assertThat(children).hasSize(count);
    assertThat(children).allMatch(MDObjectBase.class::isInstance);
    assertThat(children).allMatch(child -> List.of(names).contains(child.getName()));
    children.forEach(child -> checkChild(parentName, MDOType.COMMAND, ModuleType.CommandModule, child));
  }

  /**
   * Выполняет проверку дочерних элементов-реквизитов и табличных частей
   */
  protected void checkAttributes(List<MDOAttribute> children, int count, String parentName, AttributeType... types) {
    assertThat(children).hasSize(count);
    assertThat(children).allMatch(MDObjectBase.class::isInstance);
    assertThat(children)
      .allMatch(mdoAttribute -> List.of(types).contains(mdoAttribute.getAttributeType()));
    children.forEach(attribute -> {
      assertThat(attribute.getMdoReference())
        .isNotNull()
        .extracting(MDOReference::getType)
        .isEqualTo(MDOType.ATTRIBUTE);
      assertThat(attribute.getMdoReference().getMdoRef())
        .startsWith(parentName)
        .endsWith("." + attribute.getAttributeType().getClassName() + "." + attribute.getName());
    });
  }

  /**
   * Выполняет проверку модулей
   */
  protected void checkModules(List<MDOModule> modules, int count, String parentName, ModuleType... types) {
    assertThat(modules).hasSize(count);
    assertThat(modules)
      .allMatch(mdoModule -> List.of(types).contains(mdoModule.getModuleType()))
      .extracting(MDOModule::getUri).extracting(URI::getPath)
      .allMatch(s -> s.contains(parentName));
  }

  /**
   * Проверка на невозможность наличия модулей
   */
  protected void checkNoModules(MDObjectBase mdo) {
    assertThat(mdo).isNotInstanceOf(MDObjectBSL.class);
  }

  /**
   * Проверка на невозможность наличия дочерних объектов
   */
  protected void checkNoChildren(MDObjectBase mdo) {
    assertThat(mdo).isNotInstanceOf(MDObjectComplex.class);
  }

  /**
   * Проверка корректности заполнения дочерних элементов
   */
  private void checkChild(String parentName, MDOType type, ModuleType moduleType, MDObjectBase child) {
    checkNoChildren(child);
    assertThat(child.getMdoReference())
      .isNotNull()
      .extracting(MDOReference::getType)
      .isEqualTo(type);
    assertThat(child.getMdoReference().getMdoRef())
      .startsWith(parentName)
      .endsWith("." + type.getMDOClassName() + "." + child.getName());
    if (child instanceof MDObjectBSL) {
      checkModules(((MDObjectBSL) child).getModules(), 1,
        type.getGroupName() + "/" + child.getName(), moduleType);
    }
  }


  protected static Path getMDOPathEDT(String path) {
    return Paths.get(SRC_EDT, path);
  }

  protected static Path getMDOPathDesigner(String path) {
    return Paths.get(SRC_DESIGNER, path);
  }

  protected static Optional<MDObjectBase> getMDObjectEDT(MDOType type, String partPath) {
    return MDOFactory.readMDObject(ConfigurationSource.EDT, type, getMDOPathEDT(partPath));
  }

  protected static Optional<MDObjectBase> getMDObjectDesigner(MDOType type, String partPath) {
    return MDOFactory.readMDObject(ConfigurationSource.DESIGNER, type, getMDOPathDesigner(partPath));
  }


}
