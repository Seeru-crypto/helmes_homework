import { DefaultTheme } from 'styled-components';

const breakpoints = {
    xs: "320px",
    sm: "480px",
    md: "768px",
    lg: "1024px",
    xl: "1200px",
};

const red = {
    $300: '#FCA5A5',
    $400: '#F87171',
    $500: '#EF4444',
    $600: '#DC2626',
    $700: '#B91C1C',
};

const size = {
    $100: '8px',
    $200: '12px',
    $300: '16px',
    $400: '24px',
    $500: '32px',
    $600: '48px',
    $700: '64px',
    $800: '96px',
    $900: '128px',
};


const cyan = {
    $50: '#ecfeff',
    $100: '#cffafe',
    $200: '#a5f3fc',
    $300: '#67e8f9',
    $400: '#22d3ee',
    $500: '#06b6d4',
    $600: '#0891b2',
    $700: '#0e7490',
    $800: '#155e75',
    $900: '#164e63'
}

const gray = {
    $50: '#fffffe',
    $100: '#f1f5f9',
    $200: '#e2e8f0',
    $300: '#cbd5e1',
    $400: '#94a3b8',
    $500: '#64748b',
    $600: '#475569',
    $700: '#334155',
    $800: '#1e293b',
    $900: '#0f172a',
    $950: '#16161a'
};

export const baseTheme = {
    breakpoints,
    size,
    red,
    gray,
    cyan
};

export const DayTheme = {
    ...baseTheme,
    background: gray.$50,
    primaryColor: cyan.$600,
    primaryText: gray.$50,
    secondaryText: gray.$50,
    secondaryColor: gray.$400,
};

export const NightTheme = {
    ...baseTheme,
    background: gray.$50,
    text: "#ffffff",
    primaryColor: cyan.$300,
    secondaryColor: red.$600,
};

export type NextTheme = typeof baseTheme & (typeof DayTheme | typeof NightTheme) & DefaultTheme;
