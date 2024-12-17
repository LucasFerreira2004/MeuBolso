import { PureComponent } from 'react';
import style from './my-charts.module.css';
import {
  ComposedChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer,
} from 'recharts';

const data = [
  { name: 'Jan', despesa: 4000, receita: 2400 },
  { name: 'Feb', despesa: 3000, receita: 1398 },
  { name: 'Mar', despesa: 2000, receita: 9800 },
  { name: 'Apr', despesa: 2780, receita: 3908 },
  { name: 'May', despesa: 1890, receita: 4800 },
  { name: 'Jun', despesa: 2390, receita: 3800 },
  { name: 'Jul', despesa: 3490, receita: 4300 },
];

export default class Example extends PureComponent {
  render() {
    return (
      <div className={style.chartContainer}>
        <ResponsiveContainer width="100%" height={300}>
          <ComposedChart
            data={data}
            margin={{ top: 50, right: 20, bottom: 0, left: 20 }}
          >
            <CartesianGrid stroke="#f5f5f5" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Legend />
            <Bar dataKey="despesa" fill="#C63A22" /> {/* Coluna para Despesa */}
            <Bar dataKey="receita" fill="#2A9D8F" /> {/* Coluna para Receita */}
          </ComposedChart>
        </ResponsiveContainer>
      </div>
    );
  }
}
