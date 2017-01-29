// Copyright (C) 2016 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.googlesource.gerrit.plugins.analytics;

import com.google.inject.Singleton;

import org.eclipse.jgit.lib.Repository;
import org.gitective.core.CommitFinder;
import org.gitective.core.stat.AuthorHistogramFilter;
import org.gitective.core.stat.CommitHistogram;
import org.gitective.core.stat.CommitHistogramFilter;
import org.gitective.core.stat.UserCommitActivity;

import java.util.stream.Stream;

@Singleton
public class UserSummaryExport {

  public Stream<UserActivitySummary> getCommittersStream(Repository repo) {
    CommitFinder finder = new CommitFinder(repo);
    CommitHistogramFilter filter = new AuthorHistogramFilter();
    finder.setFilter(filter).find();
    CommitHistogram histogram = filter.getHistogram();
    UserCommitActivity[] authorActivity = histogram.getUserActivity();

    return Stream.of(authorActivity)
        .parallel()
        .map(UserActivitySummary::fromUserActivity);
  }
}