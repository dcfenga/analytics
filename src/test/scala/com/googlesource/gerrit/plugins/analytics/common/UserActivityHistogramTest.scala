// Copyright (C) 2017 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at

// http://www.apache.org/licenses/LICENSE-2.0

// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.googlesource.gerrit.plugins.analytics.common

import com.googlesource.gerrit.plugins.analytics.common.AggregationStrategy.EMAIL_YEAR
import com.googlesource.gerrit.plugins.analytics.test.GitTestCase
import org.eclipse.jgit.internal.storage.file.FileRepository
import org.scalatest.{FlatSpec, Matchers}

class UserActivityHistogramTest extends FlatSpec with Matchers with GitTestCase {

  "UserActivityHistogram" should "return no activities" in {
    val repo = new FileRepository(testRepo)
    val filter = new AggregatedHistogramFilterByDates(aggregationStrategy = EMAIL_YEAR)
    new UserActivityHistogram().get(repo, filter) should have size 0
  }

  it should "aggregate to one activity" in {
    val repo = new FileRepository(testRepo)
    add("test.txt", "content")
    val filter = new AggregatedHistogramFilterByDates(aggregationStrategy = EMAIL_YEAR)
    new UserActivityHistogram().get(repo, filter) should have size 1
  }

}