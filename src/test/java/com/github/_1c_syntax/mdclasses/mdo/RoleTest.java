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

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import org.junit.jupiter.api.Test;

class RoleTest extends AbstractMDOTest {
  RoleTest() {
    super(MDOType.ROLE);
  }

  @Override
  @Test
  void testEDT() {
    var mdo = getMDObjectEDT("Roles/Роль1/Роль1.mdo");
    checkBaseField(mdo, Role.class, "Роль1",
      "ecad0539-4f9f-491b-b0f2-f8f42d9a7c41");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }

  @Override
  @Test
  void testDesigner() {
    var mdo = getMDObjectDesigner("Roles/Роль1.xml");
    checkBaseField(mdo, Role.class, "Роль1",
      "ecad0539-4f9f-491b-b0f2-f8f42d9a7c41");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }
}
