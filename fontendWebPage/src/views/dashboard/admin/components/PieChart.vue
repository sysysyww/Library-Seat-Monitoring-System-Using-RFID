<template>
  <div :class="className" :style="{height:height,width:width}"/>
</template>

<script>
import echarts from 'echarts'

require('echarts/theme/macarons') // echarts theme
import resize from './mixins/resize'
import { fetchFilteredVisData, fetchFilteredVisDataReversed } from '@/api/libraryData'
import th from 'element-ui/src/locale/lang/th'

export default {
  name: 'ANewChart',
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '300px'
    }
  },
  data() {
    return {
      chartData: [
        { value: 0, name: '1st floor' },
        { value: 0, name: '2nd floor' },
        { value: 0, name: '3rd floor' },
        { value: 0, name: '4th floor' }
      ],
      chart: null
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.fetchPieChartData()
      this.initChart()
    })
  },

  methods: {
    fetchPieChartData() {
      Promise.all([
        fetchFilteredVisDataReversed({ floorNumber: 1 }),
        fetchFilteredVisDataReversed({ floorNumber: 2 }),
        fetchFilteredVisDataReversed({ floorNumber: 3 }),
        fetchFilteredVisDataReversed({ floorNumber: 4 })
      ]).then(responses => {
        this.chartData = responses.map((response, index) => ({
          value: response[0].emptySeats,
          name: `${index + 1}st floor`
        }))
        this.initChart() // 在这里调用 initChart
      }).catch(error => {
        console.error('Error fetching data:', error)
      })
    },

    setOptions(chartData) {
      const thisTimeData = chartData
      this.chart.setOption({
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
          left: 'center',
          bottom: '10',
          data: ['1st floor', '2nd floor', '3rd floor', '4th floor']
        },
        series: [
          {
            name: 'Library Seat Availability by Floor',
            type: 'pie',
            roseType: 'radius',
            radius: [15, 95],
            center: ['50%', '38%'],
            data: thisTimeData,
            animationEasing: 'cubicInOut',
            animationDuration: 2600
          }
        ]
      })
    },
    initChart() {
      this.chart = echarts.init(this.$el, 'macarons')
      this.setOptions(this.chartData)
    }
  }
}
</script>
