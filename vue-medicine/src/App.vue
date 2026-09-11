<template>
  <router-view />
</template>

<script setup></script>

<style>
:root {
  --brand: #176b87;
  --brand-deep: #0f4c63;
  --brand-light: #e8f6f8;
  --accent: #f2a65a;
  --page-bg: #f4f8fa;
  --surface: #ffffff;
  --text-main: #18343d;
  --text-secondary: #6e858d;
  --line: #e5eef1;
  --radius-lg: 18px;
  --radius-md: 12px;
  --shadow-card: 0 8px 30px rgba(28, 72, 87, 0.07);
  --shadow-hover: 0 14px 36px rgba(23, 107, 135, 0.14);
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html {
  min-width: 320px;
  background: var(--page-bg);
}

body {
  min-width: 320px;
  font-family: Inter, 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB',
    'Microsoft YaHei', Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: var(--text-main);
  background:
    radial-gradient(circle at 8% 0%, rgba(139, 213, 218, 0.18), transparent 28rem),
    var(--page-bg);
}

#app {
  width: 100%;
  min-height: 100vh;
}

button,
input,
textarea,
select {
  font: inherit;
}

/* Element Plus visual language */
.el-card {
  --el-card-border-color: transparent;
  border: 1px solid rgba(229, 238, 241, 0.9);
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-card);
  overflow: hidden;
}

.el-card__header {
  padding: 20px 24px;
  border-bottom-color: var(--line);
}

.el-card__body {
  padding: 24px;
}

.el-button {
  border-radius: 10px;
  font-weight: 600;
  transition: transform 0.2s ease, box-shadow 0.2s ease, background-color 0.2s ease;
}

.el-button--primary {
  --el-color-primary: var(--brand);
  --el-color-primary-light-3: #4b95aa;
  --el-color-primary-light-5: #8fc1cc;
  --el-color-primary-light-7: #c4e1e5;
  --el-color-primary-light-9: #edf8f8;
  --el-color-primary-dark-2: var(--brand-deep);
}

.el-button--primary:hover {
  box-shadow: 0 7px 16px rgba(23, 107, 135, 0.2);
  transform: translateY(-1px);
}

.el-input__wrapper,
.el-select__wrapper {
  min-height: 40px;
  border-radius: 10px;
  box-shadow: 0 0 0 1px var(--line) inset;
  transition: box-shadow 0.2s ease, background-color 0.2s ease;
}

.el-input__wrapper:hover,
.el-select__wrapper:hover {
  box-shadow: 0 0 0 1px #b9d8de inset;
}

.el-input__wrapper.is-focus,
.el-select__wrapper.is-focused {
  box-shadow: 0 0 0 2px rgba(23, 107, 135, 0.18) inset;
}

.el-table {
  --el-table-header-bg-color: #f3f8f9;
  --el-table-row-hover-bg-color: #f5fbfb;
  --el-table-border-color: var(--line);
  --el-table-text-color: #39545d;
  --el-table-header-text-color: #59727a;
  border-radius: 12px;
  overflow: hidden;
}

.el-table th.el-table__cell {
  height: 48px;
  font-weight: 700;
}

.el-table td.el-table__cell {
  height: 54px;
}

.el-page-header {
  padding: 4px 0 18px;
}

.el-page-header__left {
  color: var(--text-secondary);
}

.el-page-header__content {
  color: var(--text-main);
}

html body [class$='-list-container'],
html body [class$='-form-container'] {
  max-width: 1400px;
  margin: 0 auto;
  padding: 32px 24px 48px;
}

html body .page-title {
  color: var(--text-main);
  font-size: 19px;
  font-weight: 750;
  letter-spacing: -0.2px;
}

html body .stat-card {
  border: 0;
  color: #fff;
  background: linear-gradient(135deg, #1e8293 0%, #176b87 100%) !important;
  box-shadow: 0 12px 28px rgba(23, 107, 135, 0.16);
}

html body .stat-card .stat-value {
  font-weight: 800;
}

.el-pagination {
  --el-pagination-button-bg-color: #f3f8f9;
  --el-pagination-hover-color: var(--brand);
}

.search-card {
  background: rgba(255, 255, 255, 0.82) !important;
}

.search-card .el-form {
  display: flex;
  align-items: flex-end;
  gap: 4px 8px;
}

.search-card .el-form-item {
  margin-bottom: 0;
}

.mt-4 {
  margin-top: 24px !important;
}

@media (max-width: 768px) {
  .el-card__body {
    padding: 18px;
  }

  html body [class$='-list-container'],
  html body [class$='-form-container'] {
    padding: 22px 16px 36px;
  }

  .el-page-header {
    padding-top: 0;
  }

  .search-card .el-form {
    display: block;
  }

  .search-card .el-form-item {
    margin-bottom: 14px;
  }

  .search-card .el-form-item:last-child {
    margin-bottom: 0;
  }

  .el-table {
    font-size: 13px;
  }
}
</style>
