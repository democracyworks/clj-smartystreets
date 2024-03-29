# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to
[Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [1.1.0] - 2023-09-19

### Changed

- A fourth arity has been added to the client in order to support per-client
  query string parameters, for use with parameters such as `license`.
- Requests sent to smartystreets.com are now sent to smarty.com as part of the
  service's re-brand. This should not have user-visible impact.
- Updated dependencies.

## [1.0.0] - 2021-05-18

### Changed

- **BREAKING**: The `clj-smartystreets.core` namespace that was deprecated has
  been removed.

[unreleased]: https://github.com/democracyworks/clj-smartystreets/compare/v1.1.0...HEAD
[1.1.0]: https://github.com/democracyworks/clj-smartystreets/releases/tag/v1.1.0
[1.0.0]: https://github.com/democracyworks/clj-smartystreets/releases/tag/v1.0.0
