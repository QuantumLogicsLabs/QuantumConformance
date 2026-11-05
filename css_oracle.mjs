// css-tree's verdict on CSS values, for conformance/css_values.py.
//   node css_oracle.mjs CSS_TREE_DIR GRAMMARS.json BATCH
// CSS_TREE_DIR: an installed css-tree package (npm install css-tree).
// GRAMMARS.json: the grammars qrun uses (src/tools/gen_css_data.py --json),
// given to css-tree in place of its own, so that both match the same
// grammars. Each line of BATCH is "property<TAB>value"; prints "ok" or
// "error<TAB>message" per line.
import { readFileSync } from 'node:fs';
import { pathToFileURL } from 'node:url';
import path from 'node:path';

const [cssTreeDir, grammarsPath, batchPath] = process.argv.slice(2);
const csstree = await import(pathToFileURL(path.join(cssTreeDir, 'lib', 'index.js')).href);
const grammars = JSON.parse(readFileSync(grammarsPath, 'utf8'));

const types = {};
for (const [name, syntax] of Object.entries(grammars.types)) {
    types[name] = syntax.replaceAll('inf]', '∞]').replaceAll('inf,', '∞,');
}
const properties = {};
for (const [name, syntax] of Object.entries(grammars.properties)) {
    properties[name] = syntax.replaceAll('inf]', '∞]').replaceAll('inf,', '∞,');
}
const syntax = csstree.fork({ types, properties });

const out = [];
for (const line of readFileSync(batchPath, 'utf8').split('\n')) {
    const tab = line.indexOf('\t');
    if (tab < 0) continue;
    const name = line.slice(0, tab).toLowerCase();
    const value = line.slice(tab + 1).replace(/\r$/, '');
    let verdict;
    try {
        const ast = syntax.parse(value, { context: 'value' });
        const result = syntax.lexer.matchProperty(name, ast);
        verdict = result.matched ? 'ok' : 'error\t' + (result.error ? result.error.message.split('\n')[0] : '?');
    } catch (e) {
        verdict = 'error\t' + String(e.message).split('\n')[0];
    }
    out.push(verdict);
}
process.stdout.write(out.join('\n') + '\n');
