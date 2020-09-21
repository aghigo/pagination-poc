<template>
  <q-page class="flex flex-center">
    <q-table
      title="Testando Paginacao Server-Side"
      :data="result"
      :columns="columns"
      row-key="id"
      :rows-per-page-options="rowsPerPageOptions"
      :pagination.sync="paginationOptions"
      @request="requestData"
      :loading="isLoading"
    >
      <template v-slot:body="props">
        <q-tr :props="props">
          <q-td key="id" :props="props">
            <q-btn size="sm" color="primary" round dense @click="props.expand = !props.expand" :icon="props.expand ? 'remove' : 'add'" />
            {{ props.row.id }}
          </q-td>
          <q-td key="name" :props="props">
            {{ props.row.name }}
          </q-td>
        </q-tr>
        <q-tr :props="props" v-show="props.expand">
          <SubPagination :clientId="props.row.id" :fetchData="props.expand" />
        </q-tr>
      </template>
    </q-table>
  </q-page>
</template>

<script>
import SubPagination from 'pages/SubPagination'

export default {
  name: 'PagePagination',
  components: {
    SubPagination
  },
  data: () => {
    return {
      rowsPerPageOptions: [10],
      isLoading: false,
      paginationOptions: {
        sortBy: 'id',
        descending: false,
        page: 1,
        rowsPerPage: 10,
        rowsNumber: 0
      },
      columns: [
        {
          name: 'id',
          label: 'ID',
          field: 'id',
          required: true,
          sortable: true,
          align: 'left'
        },
        {
          name: 'name',
          label: 'Name',
          field: 'name',
          required: true,
          sortable: true,
          align: 'left'
        }
      ],
      result: []
    }
  },
  mounted () {
    this.requestData({
      pagination: this.paginationOptions,
      filter: undefined
    })
  },
  methods: {
    requestData (requestProp) {
      const quasar = this.$q

      this.isLoading = true
      quasar.loading.show({
        message: 'Carregando dados...'
      })

      const pagination = requestProp.pagination
      console.log(pagination)

      const page = pagination.page - 1
      const size = pagination.rowsPerPage
      const sortBy = pagination.sortBy === null ? this.paginationOptions.sortBy : pagination.sortBy

      pagination.descending = pagination.sortBy === null ? !pagination.descending : pagination.descending
      const descending = pagination.descending ? 'desc' : 'asc'

      this.$axios({
        method: 'GET',
        url: `http://localhost:8089/clients?page=${page}&size=${size}&sort=${sortBy},${descending}`
      }).then((response) => {
        console.log(response)
        const data = response.data

        this.result = data.content
        this.paginationOptions.page = pagination.page
        this.paginationOptions.rowsPerPage = pagination.rowsPerPage
        this.paginationOptions.sortBy = pagination.sortBy === null ? this.paginationOptions.sortBy : pagination.sortBy
        this.paginationOptions.descending = pagination.descending
        this.paginationOptions.rowsNumber = data.totalElements
      }).catch((error) => {
        console.log(error)
        quasar.notify({ type: 'negative', message: 'Nao foi possivel obter os dados.' })
      }).finally(() => {
        this.isLoading = false
        quasar.loading.hide()
      })
    }
  }
}
</script>

<style scoped>

</style>
