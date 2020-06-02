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

import static org.assertj.core.api.Assertions.assertThat;

class ChartOfCalculationTypesTest extends AbstractMDOTest {
  ChartOfCalculationTypesTest() {
    super(MDOType.CHART_OF_CALCULATION_TYPES);
  }

  @Override
  @Test
  void testEDT() {
    var mdo = getMDObjectEDT("ChartsOfCalculationTypes/ПланВидовРасчета1/ПланВидовРасчета1.mdo");
    checkBaseField(mdo, ChartOfCalculationTypes.class, "ПланВидовРасчета1",
      "1755c534-9ccd-49c4-9f8b-2aa066424aaa");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    assertThat(((MDObjectComplex) mdo).getAttributes()).hasSize(0);
    assertThat(((MDObjectBSL) mdo).getModules()).hasSize(0);

  }

  @Override
  @Test
  void testDesigner() {
    var mdo = getMDObjectDesigner("ChartsOfCalculationTypes/ПланВидовРасчета1.xml");
    checkBaseField(mdo, ChartOfCalculationTypes.class, "ПланВидовРасчета1",
      "1755c534-9ccd-49c4-9f8b-2aa066424aaa");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    assertThat(((MDObjectComplex) mdo).getAttributes()).hasSize(0);
    assertThat(((MDObjectBSL) mdo).getModules()).hasSize(0);
  }

}
