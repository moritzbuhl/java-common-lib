/*
 *  CPAchecker is a tool for configurable software verification.
 *  This file is part of CPAchecker.
 *
 *  Copyright (C) 2007-2013  Dirk Beyer
 *  All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 *  CPAchecker web page:
 *    http://cpachecker.sosy-lab.org
 */
package org.sosy_lab.common.io;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.net.URI;

import org.junit.Before;
import org.junit.Test;


public class PathsTest {

  @Before
  public void setup() {
    Paths.setFactory(null);
  }

  @Test
  public void shouldUseFileSystemAsDefault() throws Exception {
    Paths.setFactory(null);
    Path path = Paths.get("test");

    assertThat(path, instanceOf(FileSystemPath.class));
  }

  @Test
  public void shouldUseCustomFactory() throws Exception {
    AbstractPathFactory factory = mock(AbstractPathFactory.class);
    Path pathStub = mock(Path.class);

    when(pathStub.getOriginalPath()).thenReturn("stub");
    when(factory.getPath("test")).thenReturn(pathStub);

    Paths.setFactory(factory);
    Path path = Paths.get("test");

    assertEquals("stub", path.getOriginalPath());
  }

  @Test
  public void shouldConvertFileToPath() throws Exception {
    Path path = Paths.get(new File("test"));

    assertEquals("test", path.getOriginalPath());
  }

  @Test
  public void shouldConvertURItoPath() throws Exception {
    Path path = Paths.get(new URI("file:///test"));

    assertEquals("/test", path.getOriginalPath());
  }
}