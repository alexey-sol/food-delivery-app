module.exports = {
    extends: [
        "airbnb",
        "airbnb-typescript",
    ],
    parserOptions: {
        project: ["tsconfig.eslint.json"],
    },
    parser: "@typescript-eslint/parser",
    plugins: ["@typescript-eslint"],
    rules: {
        "@typescript-eslint/indent": ["error", 4],
        "@typescript-eslint/quotes": ["error", "double"],
        "implicit-arrow-linebreak": "off",
        "import/no-default-export": "error",
        "import/prefer-default-export": "off",
        indent: "off",
        "max-classes-per-file": "off",
        "no-param-reassign": ["error", {
            props: false,
        }],
        quotes: "off",
        "react/function-component-definition": ["error", {
            namedComponents: "arrow-function",
            unnamedComponents: "arrow-function",
        }],
        "react/jsx-indent": ["error", 4],
        "react/jsx-indent-props": ["error", 4],
        "react/prop-types": "off",
        "react/require-default-props": "off",
        "react/state-in-constructor": "off",
    },
};
