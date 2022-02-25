<template>
  <div :class="{ hidden: hidden }" class="pagination-container">
    <el-pagination
      :background="background"
      v-model:current-page="currentPage"
      v-model:page-size="pageSize"
      :layout="layout"
      :page-sizes="pageSizes"
      :total="total"
      v-bind="$attrs"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script>
  import { nextTick } from 'vue'
  import { scrollTo } from '@/utils/scroll-to'

  export default {
    name: 'Pagination',
    props: {
      total: {
        required: true,
        type: Number,
      },
      page: {
        type: Number,
        default: 1,
      },
      limit: {
        type: Number,
        default: 10,
      },
      pageSizes: {
        type: Array,
        default() {
          return [10, 20, 30, 50]
        },
      },
      layout: {
        type: String,
        default: 'total, sizes, prev, pager, next, jumper',
      },
      background: {
        type: Boolean,
        default: false,
      },
      autoScroll: {
        type: Boolean,
        default: true,
      },
      hidden: {
        type: Boolean,
        default: false,
      },
    },
    computed: {
      currentPage: {
        get() {
          return this.page
        },
        set(val) {
          this.$emit('update:page', val)
        },
      },
      pageSize: {
        get() {
          return this.limit
        },
        set(val) {
          this.$emit('update:limit', val)
        },
      },
    },
    methods: {
      handleSizeChange(val) {
        this.$emit('pagination', { page: this.currentPage, limit: val })
        if (this.autoScroll) {
          scrollTo(0, 800)
        }
      },
      handleCurrentChange(val) {
        nextTick(() => {
          this.$emit('pagination', { page: val, limit: this.pageSize })
        })
        if (this.autoScroll) {
          scrollTo(0, 800)
        }
      },
    },
  }
</script>

<style scoped lang="scss">
  .pagination-container {
    background: #fff;
    padding: 32px 16px;
    .el-pagination {
      text-align: right;
    }
  }
  .pagination-container.hidden {
    display: none;
  }
</style>
