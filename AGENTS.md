---
managed_by: development-hq-governance
policy_version: 1.0.0
reminder: "Revise as regras de qualidade, cobertura, complexidade e segurança deste repositório (.qlty/qlty.toml, .github/workflows/* e Actions Variables QLTY_*) antes de propor alterações ou abrir PRs."
---

# Repository Agent Guidelines

<!-- development-hq:start -->
## Governança & Qualidade (Development HQ)

Antes de implementar ou abrir Pull Requests neste repositório, verifique sempre as regras locais de qualidade e CI (`.qlty/qlty.toml`, `.github/workflows/*` e variáveis `QLTY_*` do projeto):

1. **Testes e Cobertura:**
   - Padrão alvo global: **>= 90% de cobertura geral** e **>= 90% em código novo (diff)** (confira os limites efetivos nas variáveis `QLTY_MIN_COVERAGE_TOTAL` / `QLTY_MIN_COVERAGE_DIFF` deste repositório).
   - Toda correção de bug ou nova funcionalidade deve incluir testes automatizados executáveis pela suíte nativa da stack.
   - Nunca desabilite ou ignore testes para fazer o CI passar.
2. **Complexidade e Manutenibilidade:**
   - Mantenha a complexidade ciclomática dentro do limite do projeto (`QLTY_MAX_CYCLOMATIC`, padrão `<= 10`) e evite aninhamento profundo, duplicação de código e funções monolíticas.
   - Quando o repositório usar Qlty CLI, valide localmente antes do push com `QLTY_TELEMETRY=off qlty check` (e `qlty smells` / `qlty metrics`).
3. **Segurança e Supply-Chain:**
   - **Zero segredos** no código, logs, fixtures ou histórico (Gitleaks bloqueia qualquer vazamento).
   - Não reduza permissões/travas de segurança, não afrouxe regras de CI/workflows e não adicione serviços pagos ou telemetria.
   - GitHub Actions devem ser pinadas por SHA completo.
4. **Issues e Pull Requests:**
   - Preencha `Type` (exatamente 1) e `Areas` (1 ou mais) conforme a taxonomia canônica (`type:*`, `area:*`).
   - Mantenha PRs focados, com resumo claro do impacto e dos testes executados.
<!-- development-hq:end -->

<!-- Instruções específicas deste repositório podem ser adicionadas abaixo desta linha sem serem sobrescritas pelo sync de governança. -->
