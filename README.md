# ApexUCL

**Autonomous 36-Team Swiss-Model League & Knockout Orchestration System for the UEFA Champions League**

ApexUCL is a desktop tournament management system built for the new 36-team "Swiss model" format of the UEFA Champions League. It generates the league phase fixtures, keeps the single 36-team table up to date after every result using UEFA's official tie-breakers, and carries the tournament from the league phase into the knockout play-offs and the Round of 16 bracket.

This is a semester project for **Software Design & Analysis (SDA), Fall 2026**, built up deliverable by deliverable from proposal to design to code.

---

## Why this project

In 2024 the Champions League replaced its eight four-team groups with one league table of 36 clubs. Each club now plays 8 different opponents (4 home, 4 away), two drawn from each of four seeding pots. That one change makes the tournament much harder to run by hand:

- **144 league phase fixtures** must satisfy several rules at once: two opponents from each pot, a 4 home / 4 away balance, no club facing a team from its own country, and no more than two opponents from any single foreign association.
- **One 36-team table** has to be ranked with a multi-step tie-breaker order, where a single place decides whether a club goes straight to the Round of 16, into the play-offs, or out.
- **The knockout phase** depends on exact league positions: ranks 9 to 16 are seeded against ranks 17 to 24 in two-legged play-offs, and the winners slot into fixed Round of 16 paths.

ApexUCL turns those regulations into software so the schedule, standings and bracket are always correct and verifiable.

## What it does

| Area | What the system handles |
| --- | --- |
| Club registration | Register the 36 clubs with their national association and 5-year UEFA coefficient |
| Seeding | Sort clubs by coefficient into four pots of 9 (title holder goes to Pot 1) |
| Fixture generation | Build all 144 league phase matches with a backtracking constraint solver |
| Compliance audit | Verify country protection, the foreign-association limit and 4 home / 4 away for every club |
| Rescheduling | Move a fixture while respecting a 48-hour recovery rule |
| Match results | Record scorelines and key match events; completed results update the table automatically |
| Standings | Rank all 36 clubs: Points → Goal Difference → Goals For → Away Goals → Wins → Away Wins → Coefficient |
| Qualification tiers | Split the final table into Direct R16 (1–8), Play-off (9–24) and Eliminated (25–36), colour-coded |
| Discipline | Track yellow and red cards and flag suspensions (3 yellows or 1 red) |
| Knockout play-offs | Pair seeded (9–16) with unseeded (17–24) clubs over two legs, with aggregate, extra time and penalties |
| Round of 16 | Build the fixed-path bracket from the 8 direct qualifiers and 8 play-off winners, through to the Final |

## Users

- **Tournament Admin** – configures clubs, pots and fixtures, and runs the standings and knockout stages.
- **Match Officials** – record match results, events and disciplinary incidents.
- **UEFA Coefficient Service** (external system) – supplies official club coefficients.

## Design highlights

The project applies core Gang of Four design patterns where the domain calls for them:

- **Strategy** – the tie-breaker ranking rules, so the ordering can change without touching the table logic.
- **Observer** – a completed match result notifies the standings engine to recalculate.
- **Factory** – creates two-legged knockout ties and bracket stages.

## Tech stack

| Layer | Technology |
| --- | --- |
| Language | Java (JDK 17+) |
| Desktop UI | JavaFX (or Swing) |
| Database | MySQL / Oracle / SQL Server via JDBC |
| Testing | JUnit 5, with boundary value analysis on the 8th/9th and 24th/25th place margins |
| Design | Draw.io / Visual Paradigm for UML |

## Deliverables

| # | Deliverable | Contents | Status |
| --- | --- | --- | --- |
| D2 | Project Proposal | Overview, motivation, objectives, scope, tools | ✅ Done – [PDF](docs/deliverables/GM1i230853_GM2i250789_C_D2.pdf) |
| D3 | Use Case Model | Use case diagram and 12 fully dressed use cases | ✅ Done – [PDF](docs/deliverables/GM1i230853_GM2i250789_C_D3.pdf) |
| D4 | Domain Model, SSDs & Code Integration | Domain model, 12 system sequence diagrams, Java Employee hierarchy module | 🚧 In progress |

### The 12 use cases (D3)

1. Authenticate System User
2. Manage Club Registrations & Coefficients
3. Assign Clubs to Seeding Pots
4. Generate 36-Team Swiss-Model League Fixtures
5. Validate Fixture Constraints & Country Protection
6. Reschedule League Phase Fixture
7. Record Match Events & Final Scoreline
8. Recalculate Dynamic 36-Team Standings
9. Manage Disciplinary Records & Suspensions
10. Partition Standings into Qualification Tiers
11. Seed Knockout Play-off Pairings
12. Generate Round of 16 Bracket & Progression Tree

## Repository structure

```
ApexUCL/
├── docs/
│   └── deliverables/      # Submitted course deliverables (PDF)
├── src/
│   └── com/apexucl/       # Java source code
├── .gitignore
└── README.md
```

## Running the code

Requires JDK 17 or later. The first module, Personnel & Payroll (D4 Part 3), is being added now.

```bash
javac -d out $(find src -name "*.java")
java -cp out com.apexucl.ApexUCLApp
```

## Scope

**In scope:** club registration and seeding, fixture generation and validation, match recording, standings and tie-breakers, qualification partitioning, two-legged play-offs, Round of 16 bracket, local relational storage.

**Out of scope:** public web or mobile apps, ticketing, player tracking or biometrics, financial and media-rights models, referee assignment and VAR.

## Team

| Name | Roll No. | Roles |
| --- | --- | --- |
| Muhammad Anas | 23i-0853 | Team Manager, Lead Developer, UI/UX Developer |
| Aazan Muhammad Mustafa | 25i-0789 | QA Manager, Systems Analyst |

---

*ApexUCL is an academic project and is not affiliated with or endorsed by UEFA.*
