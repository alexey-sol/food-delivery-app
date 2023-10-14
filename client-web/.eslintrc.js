module.exports = {
    extends: "./configs/eslint.base.js",
    ignorePatterns: ["build/*", "coverage/*", "dist/*", "node_modules/*"],
    overrides: [
        {
            files: ["*.ts", "*.tsx"],
            rules: {
                "no-undef": "off",
            },
        },
        {
            files: ["**/*.d.ts", "**/*.stories.*", "configs/**"],
            rules: {
                "import/no-default-export": "off",
            },
        },
        {
            files: ["*.spec.*", "*.test.*", "*-test.*"],
            env: {
                jest: true,
            },
            rules: {
                "@typescript-eslint/no-empty-function": "off",
            },
        },
    ],
};
